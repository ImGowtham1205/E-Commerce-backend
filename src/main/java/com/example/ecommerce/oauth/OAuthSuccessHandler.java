package com.example.ecommerce.oauth;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import com.example.ecommerce.service.JwtService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class OAuthSuccessHandler implements AuthenticationSuccessHandler{

	private JwtService jwtservice;
	
	public OAuthSuccessHandler(JwtService jwtservice) {
		this.jwtservice = jwtservice;
	}
	
	private static final String FRONTEND_URL = "http://localhost:5173";
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		OAuth2User oauthuser = (OAuth2User) authentication.getPrincipal();
		String email = oauthuser.getAttribute("email");
		String role = "ROLE_USER";
		String token = jwtservice.generateToken(email, role);
		
		String encodedToken = URLEncoder.encode(token, StandardCharsets.UTF_8);
		
		response.sendRedirect(FRONTEND_URL + "/oauth-success?token=" + encodedToken + "&role=" + role);	
	}
}
