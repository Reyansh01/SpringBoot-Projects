package com.SpringBoot.Project.StudentTeacherSubject.Entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "STUDENT")
public class Student {
	
//	Subject has many students and Students will have many subject..
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String name;
	
	
	
	@JsonIgnore
	@ManyToMany(mappedBy = "enrolledStudents")
	private Set<Subject> subject = new HashSet<>();


	
	public Student() {
		
	}


	public Student(int id, String name) {
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
	
	public Set<Subject> getSubject() {
		return subject;
	}
	
}
