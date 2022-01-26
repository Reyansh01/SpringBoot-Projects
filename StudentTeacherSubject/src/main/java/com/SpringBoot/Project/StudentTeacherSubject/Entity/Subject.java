package com.SpringBoot.Project.StudentTeacherSubject.Entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;


@Entity
@Table(name = "SUBJECT")
public class Subject {
	
//	Subject has many students and Students will have many subject..
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String name;
	
	
	
	@ManyToMany
	@JoinTable(name = "student_subject",
	joinColumns = @JoinColumn(name = "subject_id"),
	inverseJoinColumns = @JoinColumn(name = "student_id"))
	private Set<Student> enrolledStudents = new HashSet<>();


	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "teacher_id", referencedColumnName = "id")
	private Teacher teacher;


	
	public Subject() {
		
	}


	public Subject(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Set<Student> getEnrolledStudents() {
		return enrolledStudents;
	}
	
	public Teacher getTeacher() {
		return teacher;
	}


	
//	Extra methods
	public void enrollStudent(Student student) {
		enrolledStudents.add(student);
	}

	public void assignTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	

}
