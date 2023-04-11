package com.school.crud.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.crud.example.entity.Course;
import com.school.crud.example.entity.Student;
import com.school.crud.example.entity.Teacher;
import com.school.crud.example.repository.CourseRepository;
import com.school.crud.example.repository.TeacherRepository;

@Service
public class TeacherService {
	@Autowired
	private TeacherRepository repository;
	@Autowired
	private CourseRepository courseRepository;
	
	public Teacher saveTeacher(Teacher teacher){
	    return repository.save(teacher);
	}
	public List<Teacher> saveTeachers(List<Teacher> teachers){
	    return repository.saveAll(teachers);
	}

	public List<Teacher> getTeachers(){
		return repository.findAll();
	}
	public Teacher getTeacherById(int id){
	    return repository.findById(id).orElse(null);
	}
	public Teacher getTeacherByName(String name){
	    return repository.findByName(name);
	}
	public String deleteTeacher(int id){
	    repository.deleteById(id);
	    return "Teacher removed!! "+id;
	}

	public Teacher updateTeacher(Teacher teacher){
	    Teacher existingTeacher=repository.findById(teacher.getId()).orElse(null);
	    existingTeacher.setName(teacher.getName()==null?existingTeacher.getName():teacher.getName());
        existingTeacher.setMajor(teacher.getMajor()==null?teacher.getMajor():teacher.getMajor());

	    return repository.save(existingTeacher);
	}
	
	public Set<Course> showTeacherSchedule(int teacher_id){
	    return getTeacherById(teacher_id).tCourses();
	}
	
	public Object saveCourse(Course course, int teacher_id) {
		Teacher ourTeacher=getTeacherById(teacher_id);
		//check availability of teacher 
		if (ourTeacher==null) {
			return "no teacher with "+teacher_id+" id!!";
		}
		//check if have a time conflict with other courses
		if(repository.countOfCourseWithSameTime(teacher_id, course.getStartTime())!=0) 
			return "can not add this course " +course.getName() +" , you have another at the same time "+course.getStartTime();
		course.setMajor(ourTeacher.getMajor());
		ourTeacher.addTeachCourse(course);
	    return courseRepository.save(course);
	}
	
	public List<Course> saveCourses(List<Course> courses, int teacher_id) {
		Teacher ourTeacher=getTeacherById(teacher_id);
		//check availability of teacher 
		if (ourTeacher==null) {
			return null;
		}
		//this list contains the courses that have no issue or conflict to save it 
		List<Course> okCourses = new ArrayList<Course>();
		
		for (Course course : courses) {
			//check if have a time conflict with other courses
			if(repository.countOfCourseWithSameTime(teacher_id, course.getStartTime())!=0)
			{
				System.out.println ("can not add this course " +course.getName() +", you have another at the same time "+course.getStartTime());
				continue;
			}
			course.setMajor(ourTeacher.getMajor());
			ourTeacher.addTeachCourse(course);
			courseRepository.save(course);
			okCourses.add(course);
		}
		//check if all course caused a conflict so the 'okCourse' will be empty
		if (okCourses.isEmpty()) {
			System.out.println("No course Added!!");
			return null;
		}
	    return okCourses;
	}
	
	public Object updateCourseCapacity(int teacher_id, int course_id, int newCapacity) {
		Course updatedCourse=courseRepository.findById(course_id).orElse(null);
		Teacher ourTeacher =getTeacherById(teacher_id);
		//check availability of course
		if (updatedCourse==null) 
			return "no course with this id";
		//check availability of teacher 
		if (ourTeacher==null) 
			return "no teacher with this id";
		//check if teacher learn this course 
		if(!(ourTeacher.tCourses().contains(updatedCourse))) 
			return "Teacher does NOT teach this course";
		//check if new capacity less than old capacity
		if(newCapacity<updatedCourse.getCapacity())
			return "Can't decrease the capacity";
		
	    updatedCourse.setCapacity(newCapacity);
	    return courseRepository.save(updatedCourse);

		
	}
	
	public Set<Student> studentsInCourse(int teacher_id, int course_id) {
		Course ourCourse=courseRepository.findById(course_id).orElse(null);
		Teacher ourTeacher =getTeacherById(teacher_id);
		//check availability of course
		if (ourCourse==null)
			return null;
		//check availability of teacher 
		if (ourTeacher==null) 
			return null;
		//check if teacher learn this course 
		if (!ourTeacher.tCourses().contains(ourCourse)) {
			return null;
		}
		return ourCourse.enrollBy();
	}
	

	public String numberOfStudentInCourse(int teacher_id,int course_id) {
		Course ourCourse=courseRepository.findById(course_id).orElse(null);
		Teacher ourTeacher =getTeacherById(teacher_id);
		//check availability of course
		if (ourCourse==null) 
			return "No course with this id";
		//check availability of teacher 
		if (ourTeacher==null) 
			return "No teacher with this id";
		//check if teacher learn this course 
		if (!ourTeacher.tCourses().contains(ourCourse)) {
			return "This teacher does NOT have this course";
		}
		return ourCourse.enrollBy().size()+" of "+ourCourse.getCapacity();
	}
	
	public List<Teacher> getTeacherByMajorId(int major_id) {
		return repository.findByMajorId(major_id);
	}

}
