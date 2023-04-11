package com.school.crud.example.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.school.crud.example.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {
	
	Course findByName(String name);
	
	List<Course> findByMajorId(int id);
	
	@Query(value = "select teacher_id from teach_courses where course_id = :course_id",nativeQuery = true)
	Optional<Integer> teacherIdFromCourseId(@Param("course_id") int course_id);
	@Query(value = "select student_id from study_courses where course_id = :course_id",nativeQuery = true)
	Optional<List<Integer>> studentsIdFromCourseId(@Param("course_id") int course_id);
	@Query(value = "select Count(*) from study_courses where course_id = :course_id",nativeQuery = true)
	int enrolledBy(@Param("course_id") int course_id);
	
	

}
