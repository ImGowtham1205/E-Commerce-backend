package com.example.ecommerce.configuration;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.ecommerce.filter.JwtFilter;
import com.example.ecommerce.oauth.OAuth2Service;
import com.example.ecommerce.oauth.OAuthSuccessHandler;
import com.example.ecommerce.oauth.ProcessOAuth2UsersService;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private UserDetailsService user;
    private JwtFilter filter;
    private OAuthSuccessHandler OAuthSuccess;
    private OAuth2Service OAuthService;
    private ProcessOAuth2UsersService oauthuserservice;
    
    public SecurityConfig(UserDetailsService user ,JwtFilter filter,OAuthSuccessHandler OAuthSuccess
    		,OAuth2Service OAuthService,ProcessOAuth2UsersService oauthuserservice) {
    	this.user = user;
    	this.filter = filter;
    	this.OAuthSuccess = OAuthSuccess;
    	this.OAuthService = OAuthService;
    	this.oauthuserservice = oauthuserservice;
    }
    
    @Bean
    public SecurityFilterChain securityChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json");
                            response.getWriter().write("{\"message\":\"Invalid email or password\"}");
                        })
                    )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login","/register","/forgot-password","/reset-password").permitAll()
                        .requestMatchers("/api/products/image/**").permitAll()
                        .requestMatchers("/api/user/**").hasRole("USER")
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(user -> user.userService(OAuthService)
                        .oidcUserService(oidcUserService()))
                        .successHandler(OAuthSuccess)
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of("http://localhost:5173"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("Authorization"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public PasswordEncoder encorder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SimpleMailMessage mailMessage() {
        return new SimpleMailMessage();
    }

    @Bean
    public DaoAuthenticationProvider provider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(user);
        provider.setPasswordEncoder(encorder());
        return provider;
    }

    @Bean
    public AuthenticationManager authentication(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    
    @Bean
    public OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {

        OidcUserService delegate = new OidcUserService();

        return userRequest -> {

            OidcUser oidcUser = delegate.loadUser(userRequest);

            String email = oidcUser.getEmail();
            String name = oidcUser.getFullName();
            String provider = userRequest.getClientRegistration().getRegistrationId().toUpperCase();
            String providerid = oidcUser.getSubject();
            
            oauthuserservice.processOAuthUsers(email, name, provider, providerid);
            
            return oidcUser;
        };
    }
}
