package com.school.crud.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.crud.example.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUsername(String username);
	Boolean existsByUsername(String username);
}
