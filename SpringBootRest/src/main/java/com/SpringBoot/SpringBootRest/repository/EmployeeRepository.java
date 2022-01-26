package com.SpringBoot.SpringBootRest.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.SpringBoot.SpringBootRest.entities.Employee;

public interface EmployeeRepository extends MongoRepository<Employee, Integer> {
	
}
