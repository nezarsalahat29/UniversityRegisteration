package com.school.crud.example.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordGenertorEncoder {
	public static void main(String[] args) {
		PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
		System.out.println(passwordEncoder.encode("admin"));
	}
}
