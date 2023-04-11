package com.school.crud.example.entity;

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

public class Student {
    @Id
//    @SequenceGenerator(name="student_sequence",sequenceName="student_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	private String name;
	private int hours=0;
	@JsonIgnoreProperties({"hibernateLazyInitializer"})
	@ManyToOne
	@JoinColumn(name="major_id", nullable=false)// the object that mapping the majorStudents 
	private Major major;
	 
	@ManyToMany
	@JoinTable(
			  name = "studyCourses", 
			  joinColumns = @JoinColumn(name = "student_id"), 
			  inverseJoinColumns = @JoinColumn(name = "course_id"))
	private Set<Course> studyCourses;
	
    
	
	public void addStudyCourse(Course course) {
		studyCourses.add(course);
	}
    
	
	
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



	public int getHours() {
		return hours;
	}



	public void setHours(int hours) {
		this.hours = hours;
	}


	
	public void setMajor(Major major) {
		this.major = major;
	}
	
	public Major getMajor() {
		return major;
	}



	public Set<Course> sCourses() {
		return studyCourses;
	}



	public void setStudyCourses(Set<Course> studyCourses) {
		this.studyCourses = studyCourses;
	}
	
	
	
}
