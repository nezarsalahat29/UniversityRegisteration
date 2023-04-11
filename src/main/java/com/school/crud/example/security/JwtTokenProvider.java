package com.school.crud.example.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
	@Value("${app.jwt-secret}")
	private String jwtSecret;
	@Value("${app.jwt-expiration}")
	private long jwtExpirationDate;

	// Generate a JWT token based on the user's authentication
	public String generateToken(Authentication authentication) {
		// Get the user's name from the authentication
		String username=authentication.getName();
		// Get the current date and the expiration date of the token
		Date currentDate=new Date();
		Date expireDate=new Date(currentDate.getTime()+jwtExpirationDate);
		// Build the JWT token with the user's name, issue date, expiration date, and secret key
		String token=Jwts.builder()
		.setSubject(username)
		.setIssuedAt(new Date())
		.setExpiration(expireDate)
		.signWith(key())
		.compact();
		return token;
	}
	// Create a secret key from the JWT secret string
	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}
	// Get the username from a given JWT token
	public String getUsername(String token) {
		Claims claims=Jwts.parserBuilder()
				.setSigningKey(key())
				.build()
				.parseClaimsJws(token)
				.getBody();
		String username=claims.getSubject();
		return username;
	}
	// Validate a given JWT token
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
			.setSigningKey(key())
			.build()
			.parse(token);
			return true;
			
		} catch (Exception e) {
			throw new MalformedJwtException("Invalid Token");
		}
		
	}
}
