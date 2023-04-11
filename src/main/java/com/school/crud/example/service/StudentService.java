package com.school.crud.example.service;

import com.school.crud.example.entity.Course;
import com.school.crud.example.entity.Student;
import com.school.crud.example.payload.ScheduleDTO;
import com.school.crud.example.repository.CourseRepository;
import com.school.crud.example.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Set;

@Service
public class StudentService {
    @Autowired
    private StudentRepository repository;
    @Autowired
    private CourseRepository courseRepository;

    public Student saveStudent(Student student){
        return repository.save(student);
    }
    public List<Student> saveStudents(List<Student> students){
        return repository.saveAll(students);
    }

    public List<Student> getStudents(){
        return repository.findAll();
    }
    public Student getStudentById(int id){
        return repository.findById(id).orElse(null);
    }
    public List<Student> getStudentByName(String name){
        return repository.findByName(name);
    }
    public List<Student> getStudentByMajorId(int major_id) {

		return repository.findByMajorId(major_id);
	}
    public String deleteStudent(int id){
    	if (getStudentById(id)==null) {
			return "No Student with this id";
		}
        repository.deleteById(id);
        return "Student removed!! "+id;
    }

    public Object updateStudent(Student student){
        Student existingStudent=repository.findById(student.getId()).orElse(null);
        if (existingStudent==null) {
			return "No Student with this id";
		}
	    //check if the user pass all of required variables if not the service will take previous variable
        existingStudent.setName(student.getName()==null?existingStudent.getName():student.getName());
        existingStudent.setMajor(student.getMajor()==null?existingStudent.getMajor():student.getMajor());
        return repository.save(existingStudent);
    }
    
   
    public String enrollStudent(int student_id,int course_id) {
    	
    	Course enrolledCourse=courseRepository.findById(course_id).orElse(null);
    	Student ourStudent=repository.findById(student_id).orElse(null);
    	//check availability of course
    	if (enrolledCourse==null)  return "No Course with "+course_id+" id";
    	//check availability of student 
    	if(ourStudent==null) return "No student with "+student_id+" id";
    	//check major matching
    	if (!(enrolledCourse.getMajor().equals(ourStudent.getMajor()))) 
    		return "You must select course from your major";
    	//check time conflict
    	if(repository.findStartTimeByStudentId(student_id).contains(enrolledCourse.getStartTime())) 
    		return "You have another course in "+enrolledCourse.getStartTime();
    	
    	int totalHours=enrolledCourse.getCreditHours()+ourStudent.getHours();
    	//check if student does NOT have more 19 hour after adding the course
    	if(totalHours>19) return "Can NOT enroll more 19 hours!!";
    	//check if course has a space in capacity
    	if(courseRepository.enrolledBy(course_id)<enrolledCourse.getCapacity()) {
    		ourStudent.addStudyCourse(enrolledCourse);
    		ourStudent.setHours(totalHours);
    		repository.save(ourStudent);
    		return "Successfully enrolled";
    	}
    	else return "Full Course, Try another one!";

    }
    
    public String unEnrollStudent(int student_id,int course_id)
    {
    	Student ourStudent= repository.findById(student_id).orElse(null);
    	Course ourCourse=courseRepository.findById(course_id).orElse(null);
    	if(ourCourse==null)return "no course with this id";
    	if(ourStudent==null) return "no student with this id";
    	if(!ourStudent.sCourses().contains(ourCourse)) 
    		return "student does not studey this course";
    	Set<Course> list=ourStudent.sCourses();
    	list.remove(ourCourse);
    	ourStudent.setStudyCourses(list);
    	repository.save(ourStudent);
    	return "deleted";
    	}
    
  //using ScheduleDTO for represent a student's hour and courses.
    public ScheduleDTO showSchedule(int student_id){
    	ScheduleDTO schedule=new ScheduleDTO();
    	schedule.setCourses(getStudentById(student_id).sCourses());
    	schedule.setHours(getStudentById(student_id).getHours());
    	return schedule;
    }
	


}
