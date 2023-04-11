package com.school.crud.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.school.crud.example.payload.JwtAuthResponse;
import com.school.crud.example.payload.LoginDTO;
import com.school.crud.example.service.AuthService;

@RestController
public class AuthController {
	@Autowired
	private AuthService authService;
	
	// API for login to get a token
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDTO loginDTO){
		String token=authService.login(loginDTO);
		JwtAuthResponse response=new JwtAuthResponse();
		response.setAccessToken(token);
		return ResponseEntity.ok(response);
	}
}
