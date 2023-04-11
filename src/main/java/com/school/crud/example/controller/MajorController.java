package com.school.crud.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.school.crud.example.entity.Major;
import com.school.crud.example.service.MajorService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
public class MajorController {
	
	@Autowired
	MajorService majorService;
	
	//API for adding a one major to database
	@PostMapping("/addMajor")
    public ResponseEntity<Major> addMajor(@RequestBody Major Major){
        return new ResponseEntity<>( majorService.saveMajor(Major),HttpStatus.CREATED);
    }
	
	//API for adding multiple majors to database
    @PostMapping("/addMajors")
    public ResponseEntity<List<Major>> addMajor(@RequestBody List<Major> Majors){
        return new ResponseEntity<>( majorService.saveMajors(Majors),HttpStatus.CREATED);
    }
    
    //API for returning all majors from database
    @GetMapping("/majors")
    public ResponseEntity<List<Major>> findAllMajors(){
        return new ResponseEntity<>(majorService.getMajors(),HttpStatus.OK);
    }
    
    //API for returning a major by major_id
    @GetMapping("/majorById/{id}")
    public ResponseEntity<Major> findMajorById(@PathVariable int id){
        return new ResponseEntity<>(majorService.getMajorById(id),HttpStatus.OK);
    }
    
    //API for returning a major by major_name
    @GetMapping("/majorByName/{name}")
    public ResponseEntity<Major> findMajorByName(@PathVariable String name){
        return new ResponseEntity<>(majorService.getMajorByName(name),HttpStatus.OK);
    }
    
    //API for update a name for major
    @PutMapping("/updateMajor")
    public ResponseEntity<Major> updateMajor(@RequestBody Major Major){
        return new ResponseEntity<>(majorService.updateMajor(Major),HttpStatus.OK);
    }
    
    //API for deleting a major by id
    @SecurityRequirement(name = "Bear Authentication")
    @DeleteMapping("/deleteMajor/{id}")
    public ResponseEntity<String> deleteMajor(@PathVariable int id){
        return new ResponseEntity<>(majorService.deleteMajor(id),HttpStatus.OK);
    }

}
