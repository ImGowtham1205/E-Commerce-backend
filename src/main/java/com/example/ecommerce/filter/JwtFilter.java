package com.example.ecommerce.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.ecommerce.service.JwtService;
import com.example.ecommerce.service.UsersService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class JwtFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtService jwtservice;
	@Autowired
	private ApplicationContext context;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authheader = request.getHeader("Authorization");
		String token = null;
		String email = null;
		
		if(authheader != null && authheader.startsWith("Bearer ")) {
			token = authheader.substring(7);
			email = jwtservice.extractEmail(token);
		}
		
		if(email != null && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userdetails = context.getBean(UsersService.class).loadUserByUsername(email);
			
			if(jwtservice.validateToken(token,userdetails)) {
				UsernamePasswordAuthenticationToken authtoken =
						new UsernamePasswordAuthenticationToken(userdetails,null,userdetails.getAuthorities());
				authtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authtoken);	
				
				if(jwtservice.willExpireSoon(token, 5)) {
					String newtoken = jwtservice.refreshToken(token);
					response.setHeader("Authorization", "Bearer " + newtoken);
				}
			}
		}
		filterChain.doFilter(request, response);
	}

}
