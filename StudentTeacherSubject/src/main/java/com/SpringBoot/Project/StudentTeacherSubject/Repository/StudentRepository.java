package com.SpringBoot.Project.StudentTeacherSubject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SpringBoot.Project.StudentTeacherSubject.Entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

}
