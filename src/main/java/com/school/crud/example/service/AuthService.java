package com.school.crud.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.school.crud.example.payload.LoginDTO;
import com.school.crud.example.security.JwtTokenProvider;

@Service
public class AuthService {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	public String login(LoginDTO loginDTO) {
			//authenticate a user that login and generate token for it if it is has permission.
			Authentication authentication=authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String token=jwtTokenProvider.generateToken(authentication);
			return token;
				
	}
}
