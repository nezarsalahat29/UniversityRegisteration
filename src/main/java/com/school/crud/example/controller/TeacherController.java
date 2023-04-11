package com.school.crud.example.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.school.crud.example.entity.Course;
import com.school.crud.example.entity.Student;
import com.school.crud.example.entity.Teacher;
import com.school.crud.example.service.TeacherService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
public class TeacherController {
	 	@Autowired
	    private TeacherService teacherService;
	 	
		//API for adding a one teacher to database and returning a response contains the teacher
	    @PostMapping("/addTeacher")
	    public ResponseEntity<Teacher> addTeacher(@RequestBody Teacher teacher){
	        return new ResponseEntity<>(teacherService.saveTeacher(teacher),HttpStatus.CREATED);
	    }
	    
		//API for adding multiple teachers to database and returning a response contains new teachers
	    @PostMapping("/addTeachers")
	    public ResponseEntity<List<Teacher>> addTeacher(@RequestBody List<Teacher> teachers){
	        return new ResponseEntity<>(teacherService.saveTeachers(teachers),HttpStatus.CREATED);
	    }
	    
		//API for return all teachers from database
	    @GetMapping("/teachers")
	    public ResponseEntity<List<Teacher>> findAllTeachers(){
	        return new ResponseEntity<>(teacherService.getTeachers(),HttpStatus.OK);
	    }
	    
		//API for return teacher by teacher_id from database
	    @GetMapping("/teacherById/{id}")
	    public ResponseEntity<Teacher> findTeacherById(@PathVariable int id){
	        return new ResponseEntity<>(teacherService.getTeacherById(id),HttpStatus.OK);
	    }
	    
	    //API for return course by course name from database
	    @GetMapping("/teacherByName/{name}")
	    public ResponseEntity<Teacher> findTeacherByName(@PathVariable String name){
	        return new ResponseEntity<>(teacherService.getTeacherByName(name),HttpStatus.OK);
	    }
	    
	    //API for return teacher by teacher_id from database
	    @GetMapping("/teacherByMajorId/{major_id}")
	    public ResponseEntity<List<Teacher>> findTeacherByMajorId(@PathVariable int major_id){
	        return new ResponseEntity<>(teacherService.getTeacherByMajorId(major_id),HttpStatus.OK);
	    }
	    
	    //API for updating (name, major) for teacher and return it 
	    @PutMapping("/updateTeacher")
	    public ResponseEntity<Teacher> updateTeacher(@RequestBody Teacher teacher){
	        return new ResponseEntity<>(teacherService.updateTeacher(teacher),HttpStatus.OK);
	    }
	    
	    //API for deleting teacher from database by teacher_id
	    @SecurityRequirement(name = "Bear Authentication")
	    @DeleteMapping("/deleteTeacher/{id}")
	    public ResponseEntity<String> deleteTeacher(@PathVariable int id){
	        return new ResponseEntity<>(teacherService.deleteTeacher(id),HttpStatus.OK);
	    }
	    //API for showing student schedule
	    @GetMapping("/showTeacherSchedule/{teacher_id}")
	    public ResponseEntity<Set<Course>> showSchedule(@PathVariable int teacher_id){
	    	return new ResponseEntity<>(teacherService.showTeacherSchedule(teacher_id),HttpStatus.OK);
	    }
	    //API for adding course for a teacher to database and returning a response contains new course
	    @PostMapping("/teacherAddCourse")
	    public ResponseEntity<Object> addCourse(@RequestBody Course Course,@RequestParam int teacher_id){
	        return new ResponseEntity<>(teacherService.saveCourse(Course,teacher_id),HttpStatus.OK);
	    }
	    
	    //API for adding multiple courses for a teacher to database and returning a response contains new courses
	    @PostMapping("/teacherAddCourses")
	    public ResponseEntity<List<Course>> addCourse(@RequestBody List<Course> courses,@RequestParam int teacher_id){
	        return new ResponseEntity<>(teacherService.saveCourses(courses,teacher_id),HttpStatus.OK);
	    }
	    
	    //API for updating the capacity of teacher's specific course
	    @PutMapping("/teacherUpdateCapacity")
	    public ResponseEntity<Object> updateCourseCapacity(@RequestBody Map<String, Integer> updateMap) {
	    	
	    	return new ResponseEntity<>(teacherService.updateCourseCapacity(updateMap.get("teacher_id")
	    			,updateMap.get("course_id"),updateMap.get("newCapacity")),HttpStatus.OK);
	    	
	    }
	    
	   //API for returning a list of students that enrolled in specific course 
	   @GetMapping("/studentsInCourse")
	   public ResponseEntity<Set<Student>> studentsInCourse(@RequestBody Map<String, Integer> infoMap){
		   
		   return new ResponseEntity<>(teacherService.studentsInCourse(infoMap.get("teacher_id"),infoMap.get("course_id")),HttpStatus.OK);
	   }
	   
	   //API for returning a number of students that enrolled in specific course 
	   @GetMapping("/numberOfStudent")
	   public ResponseEntity<String> numberOfStudent(@RequestBody Map<String, Integer> infoMap) {
		   return new ResponseEntity<>(teacherService.numberOfStudentInCourse(infoMap.get("teacher_id"),infoMap.get("course_id")),HttpStatus.OK);
	   }
}
