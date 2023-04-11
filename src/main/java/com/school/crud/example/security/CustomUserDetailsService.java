package com.school.crud.example.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.school.crud.example.entity.User;
import com.school.crud.example.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Find the user by username
		 User user = userRepository.findByUsername(username).orElseThrow(()->
		 new UsernameNotFoundException("User not found with username: " +username));
		// Create a set of authorities for the user's roles
		 Set<GrantedAuthority> authorities=user
				 .getRoles()
				 .stream()
				 .map((role)->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
		// Return a new user details object with the user's username, password, and authorities
		return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
	}

}
