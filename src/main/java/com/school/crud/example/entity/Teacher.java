package com.school.crud.example.entity;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {
	@Id
//    @SequenceGenerator(name="teacher_sequence",sequenceName="teacher_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	private String name;
	@JsonIgnoreProperties({"hibernateLazyInitializer"})
	@ManyToOne
	@JoinColumn(name="major_id", nullable=false)// the object that mapping the majorTeachers 
	private Major major;
	
	@ManyToMany
	@JoinTable(
			  name = "teachCourses", 
			  joinColumns = @JoinColumn(name = "teacher_id"), 
			  inverseJoinColumns = @JoinColumn(name = "course_id"))
	private Set<Course> teachCourses;
	
	
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
	public Major getMajor() {
		return major;
	}
	public void setMajor(Major major) {
		this.major = major;
	}
	public Set<Course> tCourses() {
		return teachCourses;
	}
	public void setTeachCourses(Set<Course> teachCourses) {
		this.teachCourses = teachCourses;
	}
	
	
	public void addTeachCourse(Course course) {
		teachCourses.add(course);
	}
	public void addTeachCourses(List<Course> course) {
		teachCourses.addAll(course);
	}
	
	
	
}
