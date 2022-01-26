package com.SpringBoot.Project.StudentTeacherSubject.Controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.SpringBoot.Project.StudentTeacherSubject.Entity.Student;
import com.SpringBoot.Project.StudentTeacherSubject.Repository.StudentRepository;

@RestController
public class StudentController {
	
//	Subject has many students and Students have many subjects..
	
	@Autowired
	StudentRepository studentRepository;
	
	private static final Logger LOGGER = LogManager.getLogger(StudentController.class);
	
	@PostMapping("/addStudent")
	public ResponseEntity<?> addStudent(@RequestBody Student student) {
		try {
			if(studentRepository.existsById(student.getId())) {
				return ResponseEntity.ok("You cannot override the existing ID of the student.");
			} 
			else {
				Student stu = studentRepository.save(student);
				return ResponseEntity.status(HttpStatus.CREATED).body(stu);
			}
			
		} catch(Exception e) {
			LOGGER.error("Error occurred while adding Student.", e);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	
	@GetMapping("/getStudent")
	public ResponseEntity<List<Student>> getStudent() {
		try {
			return ResponseEntity.status(HttpStatus.FOUND).body(studentRepository.findAll());
		} catch(Exception e) {
			LOGGER.error("Error occurred while fetching students.", e);
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
