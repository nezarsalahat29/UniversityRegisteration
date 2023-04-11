package com.school.crud.example.controller;

import com.school.crud.example.entity.Student;
import com.school.crud.example.payload.EnrollmentInput;
import com.school.crud.example.payload.ScheduleDTO;
import com.school.crud.example.service.StudentService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class StudentController {
    @Autowired
    private StudentService studentService;
    
	//API for adding a student to database and returning a response contains new student
    @PostMapping("/addStudent")
    public ResponseEntity<Student> addStudent(@RequestBody Student student){
        return new ResponseEntity<>(studentService.saveStudent(student),HttpStatus.CREATED);
    }
    
	//API for adding multiple students to database and returning a response contains new students
    @PostMapping("/addStudents")
    public ResponseEntity<List<Student>> addStudent(@RequestBody List<Student> students){
        return new ResponseEntity<>( studentService.saveStudents(students),HttpStatus.CREATED);
    }
    
	//API for returning all students from database
    @GetMapping("/students")
    public ResponseEntity<List<Student>> findAllStudents(){
        return new ResponseEntity<>(studentService.getStudents(),HttpStatus.OK);
    }
    
    //API for returning student by student_id from database
    @GetMapping("/studentById/{id}")
    public ResponseEntity<Student> findStudentById(@PathVariable int id){
    	Student student=studentService.getStudentById(id);
    	if (student==null) {
			return new ResponseEntity<>(student,HttpStatus.NOT_FOUND);
		}
        return new ResponseEntity<>(student,HttpStatus.OK);
    }
    
    //API for returning student by student name from database and return it
    @GetMapping("/studentByName/{name}")
    public ResponseEntity<List<Student>> findStudentByName(@PathVariable String name){
        return new ResponseEntity<>(studentService.getStudentByName(name),HttpStatus.OK);
    }
    //API for returning students that have a specific major_id from database
    @GetMapping("/studentByMajorId/{major_id}")
    public ResponseEntity<List<Student>> findStudentByMajorId(@PathVariable int major_id) {
    	return  new ResponseEntity<>(studentService.getStudentByMajorId(major_id),HttpStatus.OK);
    }
    
    //API for updating (name, major) for course 
    @PutMapping("/updateStudent")
    public ResponseEntity<Object> updateStudent(@RequestBody Student student){
        return  new ResponseEntity<>(studentService.updateStudent(student),HttpStatus.OK);
    }
    
    //API for deleting student from database by id
    @SecurityRequirement(name = "Bear Authentication")
    @DeleteMapping("/deleteStudent/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable int id){
        return  new ResponseEntity<>(studentService.deleteStudent(id),HttpStatus.OK);
    }
    
    //API for enroll student in course and return the status of operation
    @PostMapping("/enrollStudent")
    public ResponseEntity<String> enrollStudent(@RequestBody EnrollmentInput input) {
    	return new ResponseEntity<>(studentService.enrollStudent(input.getStudent_id() ,input.getCourse_id()),HttpStatus.OK);
    }
    
    //API for showing student schedule
    @GetMapping("/showSchedule/{student_id}")
    public ResponseEntity<ScheduleDTO> showSchedule(@PathVariable int student_id){
    	return new ResponseEntity<>(studentService.showSchedule(student_id),HttpStatus.OK);
    }

}


