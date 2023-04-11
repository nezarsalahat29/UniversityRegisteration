package com.school.crud.example.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class Major {
	@Id
//	@SequenceGenerator(name="major_sequence",sequenceName="major_sequence",allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	@JsonIgnore 
	@OneToMany(mappedBy = "major")// major object in Student entity
	Set<Student> majorStudents;
	@JsonIgnore 
	@OneToMany(mappedBy = "major")// major object in Course entity
	Set<Course> majorCourses;
	@JsonIgnore 
	@OneToMany(mappedBy = "major")// major object in Teacher entity
	Set<Teacher> majorTeachers;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	 
	 
}