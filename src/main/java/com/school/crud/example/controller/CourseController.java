package com.school.crud.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.school.crud.example.entity.Course;

import com.school.crud.example.service.CourseService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
public class CourseController {
	@Autowired
	private CourseService courseService;

	//API for adding a one course to database and returning a response contains the course
	@SecurityRequirement(name = "Bear Authentication")
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/addCourse")
    public ResponseEntity<Course> addCourse(@RequestBody Course Course){
        return new ResponseEntity<>(courseService.saveCourse(Course),HttpStatus.CREATED);
    }
	
	//API for adding multiple courses to database and returning a response contains new courses
    @PostMapping("/addCourses")
    public ResponseEntity<List<Course>> addCourse(@RequestBody List<Course> courses){
        return new ResponseEntity<>(courseService.saveCourses(courses),HttpStatus.CREATED);
    }
    
	//API for returning all courses from database
    @GetMapping("/courses")
    public ResponseEntity<List<Course>> findAllCourses(){
        return new ResponseEntity<>(courseService.getCourses(),HttpStatus.OK);
    }
    
	//API for returning course by course id from database
    @GetMapping("/courseById/{id}")
    public ResponseEntity<Course> findCourseById(@PathVariable int id){
        return new ResponseEntity<>(courseService.getCourseById(id),HttpStatus.OK);
    }
    
    //API for returning course by course name from database
    @GetMapping("/courseByName/{name}")
    public ResponseEntity<Course> findCourseByName(@PathVariable String name){
        return new ResponseEntity<>(courseService.getCourseByName(name),HttpStatus.OK);
    }
    
    //API for returning courses that have a specific major_id from database
    @GetMapping("/courseByMajorId/{major_id}")
    public ResponseEntity<List<Course>> findCourseByMajorId(@PathVariable int major_id) {
    	return new ResponseEntity<>(courseService.getCourseByMajorId(major_id),HttpStatus.OK);
    }
    
    
    //API for updating (name, capacity, credit_hours, start_time, end_time, major) for course and return it 
    @PutMapping("/updateCourse")
    public ResponseEntity<Object> updateCourse(@RequestBody Course course){
        return new ResponseEntity<>(courseService.updateCourse(course),HttpStatus.OK);
    }
    
    //API for deleting course from database by course_id
    @SecurityRequirement(name = "Bear Authentication")
    @DeleteMapping("/deleteCourse/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable int id){
    	
        return new ResponseEntity<>(courseService.deleteCourse(id),HttpStatus.OK);
    }

}
