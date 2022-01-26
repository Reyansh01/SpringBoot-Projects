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

import com.SpringBoot.Project.StudentTeacherSubject.Entity.Teacher;
import com.SpringBoot.Project.StudentTeacherSubject.Repository.TeacherRepository;

@RestController
public class TeacherController {
	
//	Teacher can teach many subject but one subject has only one teacher..
	
	@Autowired
	TeacherRepository teacherRepository;
	
	private static final Logger LOGGER = LogManager.getLogger(TeacherController.class);
	
	@PostMapping("/addTeacher")
	public ResponseEntity<?> addTeacher(@RequestBody Teacher teacher) {
		try {
			if(teacherRepository.existsById(teacher.getId())) {
				return ResponseEntity.ok("You cannot override the existing ID of the teacher.");
			} 
			else {
				Teacher teach = teacherRepository.save(teacher);
				return ResponseEntity.status(HttpStatus.CREATED).body(teach);
			}
			
		} catch(Exception e) {
			LOGGER.error("Error occurred while adding Teacher.", e);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	
	@GetMapping("/getTeacher")
	public ResponseEntity<List<Teacher>> getTeacher() {
		try {
			return ResponseEntity.status(HttpStatus.FOUND).body(teacherRepository.findAll());
		} catch(Exception e) {
			LOGGER.error("Error occurred while fetching teachers.", e);
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
