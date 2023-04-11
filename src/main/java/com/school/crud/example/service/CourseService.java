package com.school.crud.example.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.school.crud.example.entity.Course;
import com.school.crud.example.entity.Student;
import com.school.crud.example.entity.Teacher;
import com.school.crud.example.repository.CourseRepository;
import com.school.crud.example.repository.StudentRepository;
import com.school.crud.example.repository.TeacherRepository;

@Service
public class CourseService {
	@Autowired
	private CourseRepository repository;
	@Autowired
	private TeacherRepository teacherRepository;
	@Autowired
	private StudentRepository studentRepository;
	public Course saveCourse(Course course){
	    return repository.save(course);
	}
	
	public List<Course> saveCourses(List<Course> courses){
		
	    return repository.saveAll(courses);
	}

	public List<Course> getCourses(){
		return repository.findAll();
	}
	
	public Course getCourseById(int id){
	    return repository.findById(id).orElse(null);
	}
	
	public Course getCourseByName(String name){
	    return repository.findByName(name);
	}
	public List<Course> getCourseByMajorId(int major_id) {
		return repository.findByMajorId(major_id);
	}
	
	public String deleteCourse(int id){
		//get a teacher which teach the deleted course
		Course ourCourse=getCourseById(id);
        Teacher ourTeacher=teacherRepository.findById(repository.teacherIdFromCourseId(id).orElse(0)).orElse(null);
		if (ourTeacher!=null) {
			//Remove the course from teach_courses by remove it from teacher courses
			Set<Course> teacherCourses=ourTeacher.tCourses();
			teacherCourses.remove(ourCourse);
			//update the set in our teacher
			ourTeacher.setTeachCourses(teacherCourses);
			//save the teacher
			teacherRepository.save(ourTeacher);
		}
		//get students which enrolled in the deleted course
		List<Student> ourStudents=studentRepository.findAllById(repository.studentsIdFromCourseId(id).orElse(null));
		if (!ourStudents.isEmpty()) {
			//update all students by delete the deleted course from studyCourses and update students hour
			for (Student student : ourStudents) {
				Set<Course> studentCourses=student.sCourses();
				studentCourses.remove(ourCourse);
				student.setStudyCourses(studentCourses);
				student.setHours(student.getHours()-ourCourse.getCreditHours());
				//save the student
				studentRepository.save(student);
			}
		}
		
	    repository.deleteById(id);
	    return "Course removed!! "+id;
	}

	public Object updateCourse(Course course){
	    Course existingCourse=repository.findById(course.getId()).orElse(null);
	    if (existingCourse==null) {
			return "No course with this id";
		}
	    //check if the user pass all of required variables if not the service will take previous variable
	    existingCourse.setName(course.getName()==null?existingCourse.getName():course.getName());
	    existingCourse.setCapacity(course.getCapacity()==0?existingCourse.getCapacity():course.getCapacity());
	    existingCourse.setCreditHours(course.getCreditHours()==0?existingCourse.getCreditHours():course.getCreditHours());
	    existingCourse.setStartTime(course.getStartTime()==null?existingCourse.getStartTime():course.getStartTime());
	    existingCourse.setEndTime(course.getEndTime()==null?existingCourse.getEndTime():course.getEndTime());
	    existingCourse.setMajor(course.getMajor()==null?existingCourse.getMajor():course.getMajor());
	    return repository.save(existingCourse);
	}
	
	
	

}
