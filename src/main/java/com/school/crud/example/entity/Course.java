package com.school.crud.example.entity;
import java.sql.Time;
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
public class Course {
    @Id
//    @SequenceGenerator(name="course_sequence",sequenceName="course_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	private int capacity=1;
	private int creditHours=3;
	private Time startTime;
	private Time endTime;
	@JsonIgnoreProperties({"hibernateLazyInitializer"})
	@ManyToOne
	@JoinColumn(name="major_id", nullable=false)
	private Major major;
	 
	@ManyToMany(mappedBy = "teachCourses")// a set of teachers in Course entity
	Set<Teacher> teachedBy;
	 
	@ManyToMany(mappedBy = "studyCourses")// a set of students in Course entity
	private Set<Student>enrollBy;
	 
	 
	

	
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

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getCreditHours() {
		return creditHours;
	}

	public void setCreditHours(int creditHours) {
		this.creditHours = creditHours;
	}

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}

	public Set<Teacher> teachedBy() {
		return teachedBy;
	}

	public void setTeachedBy(Set<Teacher> teachedBy) {
		this.teachedBy = teachedBy;
	}

	public Set<Student> enrollBy() {
		return enrollBy;
	}

	public void setEnrollBy(Set<Student> enrollBy) {
		this.enrollBy = enrollBy;
	}

	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

	
	

	
}
