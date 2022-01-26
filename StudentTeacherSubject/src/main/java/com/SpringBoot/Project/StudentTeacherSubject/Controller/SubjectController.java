package com.SpringBoot.Project.StudentTeacherSubject.Controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.SpringBoot.Project.StudentTeacherSubject.Entity.Student;
import com.SpringBoot.Project.StudentTeacherSubject.Entity.Subject;
import com.SpringBoot.Project.StudentTeacherSubject.Entity.Teacher;
import com.SpringBoot.Project.StudentTeacherSubject.Repository.StudentRepository;
import com.SpringBoot.Project.StudentTeacherSubject.Repository.SubjectRepository;
import com.SpringBoot.Project.StudentTeacherSubject.Repository.TeacherRepository;

@RestController
public class SubjectController {
	
//	Subject has many students and Students will have many subject..
	
	@Autowired 
	SubjectRepository subjectRepository;
	
	@Autowired 
	StudentRepository studentRepository;
	
	@Autowired 
	TeacherRepository teacherRepository;
	
	private static final Logger LOGGER = LogManager.getLogger(SubjectController.class);
	
	
	@PostMapping("/addSubject")
	public ResponseEntity<?> addSubject(@RequestBody Subject subject) {
		try {
			if(subjectRepository.existsById(subject.getId())) {
				return ResponseEntity.ok("You cannot override the existing ID of the subject.");
			} 
			else {
				Subject sub = subjectRepository.save(subject);
				return ResponseEntity.status(HttpStatus.CREATED).body(sub);
			}
		} catch(Exception e) {
			LOGGER.error("Exception occured while saving subject.", e);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	
	@PutMapping("/subject/{subjectid}/student/{studentid}")
	public ResponseEntity<Subject> enrollStudentToSubject(@PathVariable int subjectid, @PathVariable int studentid) {
		try {
//			Applied get() because findById returns optional so it can give an error.
			Subject subject = subjectRepository.findById(subjectid).get();
			Student student = studentRepository.findById(studentid).get();
			subject.enrollStudent(student);
			Subject sub = subjectRepository.save(subject);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(sub);
		} catch(Exception e) {
			LOGGER.error("Error occurred while enrolling student to subject.", e);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();		
	}
	
	
	@PutMapping("/subject/{subjectid}/teacher/{teacherid}")
	public ResponseEntity<Subject> assignTeacherToSubject(@PathVariable int subjectid, @PathVariable int teacherid) {
		try {
			Subject subject = subjectRepository.findById(subjectid).get();
			Teacher teacher = teacherRepository.findById(teacherid).get();
			subject.assignTeacher(teacher);
			Subject sub = subjectRepository.save(subject);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(sub);
		} catch(Exception e) {
			LOGGER.error("Error occurred while assigning teacher to a subject.", e);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	
	@GetMapping("/getSubject")
	public ResponseEntity<List<Subject>> getSubject() {
		try {
			return ResponseEntity.status(HttpStatus.FOUND).body(subjectRepository.findAll());
		} catch(Exception e) {
			LOGGER.error("Error occurred while fetching subjects.", e);
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
