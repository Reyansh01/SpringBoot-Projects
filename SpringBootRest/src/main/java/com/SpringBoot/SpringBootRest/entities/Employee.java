package com.SpringBoot.SpringBootRest.entities;

import java.util.LinkedHashMap;

import org.springframework.data.mongodb.core.mapping.Document;

//Mongo DB is No-SQL Document type database..
@Document("employees")
public class Employee {
	
	private int id;
	private String name;
	private String username;
	private String email;
	LinkedHashMap<String, Object> address = new LinkedHashMap<>();
	private String phone;
	private String website;
	LinkedHashMap<String, Object> company = new LinkedHashMap<>();
	
//	Parameterized constructor
	public Employee(int id, String name, String username, String email, String phone, String website) {
		this.id = id;
		this.name = name;
		this.username = username;
		this.email = email;
		this.phone = phone;
		this.website = website;
	}
	
//	Getters and Setters
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
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	
	
	public LinkedHashMap<String, Object> getCompany() {
		return company;
	}
	
	public void setCompany(String name, String catchPhrase, String bs) {
		address.put(name, this);
		address.put(catchPhrase, this);
		address.put(bs, this);
	}

	
	public LinkedHashMap<String, Object> getAddress() {
		return address;
	}
	
	public void setAddress(String street, String suite, String city, String zipcode) {
		address.put(street, this);
		address.put(suite, this);
		address.put(city, this);
		address.put(zipcode,  this);
	}
	
}
