package com.school.crud.example.repository;

import com.school.crud.example.entity.Student;

import java.sql.Time;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface StudentRepository extends JpaRepository<Student,Integer> {
	
	List<Student> findByMajorId(int id);
	
	@Query(value = "select * from student where name LIKE BINARY :firstName",nativeQuery = true)
	List<Student> findByName(@Param("firstName")String name);
	
	@Query(value = "select start_time from course where id IN (select course_id from study_courses where student_id= :student_id )",nativeQuery = true)
	Set<Time> findStartTimeByStudentId(@Param("student_id") int student_id);
	
	


}
