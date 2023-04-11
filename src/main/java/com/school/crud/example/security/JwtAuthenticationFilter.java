package com.school.crud.example.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private UserDetailsService userDetailsService;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		// Get token from the request header
		String token=getTokenFromRequest(request);
		

		// Validate token
		if(StringUtils.hasText(token)&&jwtTokenProvider.validateToken(token)) {
			
			// Get username from the token
			String username=jwtTokenProvider.getUsername(token);
			
			// Load user details from the database
			UserDetails userDetails=userDetailsService.loadUserByUsername(username);
			// Create authentication token with user details and set it to SecurityContextHolder
			UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(
					userDetails,
					null,
					userDetails.getAuthorities()
					);
			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		
		}
		// Call next filter in the chain
		filterChain.doFilter(request, response);
		
	}
	private String getTokenFromRequest(HttpServletRequest request) {
		String bearerToken=request.getHeader("Authorization");
		if(StringUtils.hasText(bearerToken)&& bearerToken.startsWith("Bearer "))
			// If header contains Authorization and starts with Bearer, return the token
			return bearerToken.substring(7,bearerToken.length());
			
		return null;
	}

}
