package com.school.crud.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.school.crud.example.security.JwtAuthenticationEntryPoint;
import com.school.crud.example.security.JwtAuthenticationFilter;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@EnableMethodSecurity
@SecurityScheme(
		name = "Bear Authentication",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		scheme = "bearer"
		)
public class SecurityConfig  {
	// Inject JwtAuthenticationEntryPoint and JwtAuthenticationFilter 
	@Autowired
	private JwtAuthenticationEntryPoint authenticationEntryPoint;
	@Autowired
	private JwtAuthenticationFilter authenticationFilter;
	
	// Create a PasswordEncoder bean that uses the BCrypt algorithm
	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	// Create an AuthenticationManager bean that is used to authenticate requests
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
	}
	// Configure the security filter chain that will be used by the application
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
		// Disable Cross-Site Request Forgery (CSRF) protection
		http.csrf().disable()
		 // Set up request authorization for HTTP GET, POST, and PUT methods, as well as for some specific URLs related to Swagger UI
		.authorizeHttpRequests((authorize)->authorize
				.requestMatchers(HttpMethod.GET).permitAll()
				.requestMatchers(HttpMethod.POST).permitAll()
				.requestMatchers(HttpMethod.PUT).permitAll()
				.requestMatchers("/v3/api-docs/**").permitAll()
				.requestMatchers("/swagger-ui/**").permitAll()
				.requestMatchers("/swagger-resource/**").permitAll()
				.requestMatchers("/swagger-ui.html/**").permitAll()
				.requestMatchers("/webjars/**").permitAll()
				.anyRequest().authenticated())
		// Configure an authentication entry point to handle authentication errors
		.exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint))
		// Configure the session management policy for the application. In this case, 
		//SessionCreationPolicy.STATELESS is used, which means that the application will not create an HTTP session
		.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		// Add the authenticationFilter bean before the UsernamePasswordAuthenticationFilter, which is used by Spring Security to authenticate requests
		http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
}
