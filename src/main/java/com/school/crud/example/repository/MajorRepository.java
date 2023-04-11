package com.school.crud.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.school.crud.example.entity.Major;

public interface MajorRepository extends JpaRepository<Major, Integer>{
	
	Major findByName(@Param("majorName") String majorName);

}
