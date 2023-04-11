package com.school.crud.example.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.crud.example.entity.Major;
import com.school.crud.example.repository.MajorRepository;
@Service
public class MajorService {
	@Autowired
	MajorRepository majorRepository;
	public Major saveMajor(Major major){
        return majorRepository.save(major);
    }
    public List<Major> saveMajors(List<Major> majors){
        return majorRepository.saveAll(majors);
    }

    public List<Major> getMajors(){
        return majorRepository.findAll();
    }
    public Major getMajorById(int id){
        return majorRepository.findById(id).orElse(null);
    }
    public Major getMajorByName(String name){
        return majorRepository.findByName(name);
    }
    public String deleteMajor(int id){
    	majorRepository.deleteById(id);
        return "major removed!! "+id;
    }

    public Major updateMajor(Major major){
        Major existingmajor=majorRepository.findById(major.getId()).orElse(null);
        existingmajor.setName(major.getName());
        return majorRepository.save(existingmajor);
    }
    
    
    
   
}
