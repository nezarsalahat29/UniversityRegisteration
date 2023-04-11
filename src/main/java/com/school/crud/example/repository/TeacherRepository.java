package com.school.crud.example.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.school.crud.example.entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
	@Query(value = "select * from teacher where name LIKE BINARY :teacherName",nativeQuery = true)
	Teacher findByName(@Param("teacherName") String teacherName);
	
	@Query(value = "select Count(*) from teach_courses where teacher_id= :teacher_id and course_id in(select id from course where start_time= :start_time) ",nativeQuery = true)
	int countOfCourseWithSameTime(@Param("teacher_id") int teacher_id,@Param("start_time") java.sql.Time start_time);

	List<Teacher> findByMajorId(int id);
}
 

