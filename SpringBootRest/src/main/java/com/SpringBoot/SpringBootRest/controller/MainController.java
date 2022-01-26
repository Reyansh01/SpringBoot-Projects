package com.SpringBoot.SpringBootRest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SpringBoot.SpringBootRest.entities.Employee;
import com.SpringBoot.SpringBootRest.repository.EmployeeRepository;


//@CrossOrigin("localhost:3000")
@RestController
public class MainController {
	
	@Autowired
	private EmployeeRepository employeeRepository;

//	Used Logger for internal logging.
	private static final Logger LOGGER = LogManager.getLogger(MainController.class);
	
	
//	ResponseEntity represents whole of the HTTP response.
	@PostMapping("/addEmployee")
	public ResponseEntity<?> addEmployee(@RequestBody Employee employee) {
		try {
			if(employeeRepository.existsById(employee.getId())) {
				return ResponseEntity.ok("You cannot override the existing ID.");
			}
			else {
				Employee details = this.employeeRepository.save(employee);
//				return ResponseEntity.ok(details);
				return ResponseEntity.status(HttpStatus.CREATED).body(details);
			}
			
		} catch(Exception e) {
			LOGGER.error("Exception occured while saving details", e);
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		
	}
	
	
//	Used List because it will return the list of employees.
	@GetMapping("/getEmployee")
	public ResponseEntity<List<Employee>> getEmployee() {
		try {
			return ResponseEntity.ok(this.employeeRepository.findAll());
		} catch(Exception e) {
			LOGGER.error("Exception occured while fetching details", e);
		}

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	
	}
	
	
//	Used optional because it may or may not contain any value. 
	@GetMapping("/getEmployee/{id}")
	public ResponseEntity<Optional<Employee>> getEmployeeByID(@PathVariable Integer id) {
		try {
			Optional<Employee> emp = this.employeeRepository.findById(id);
			if(emp.isEmpty()) {
//				LOGGER.error("This id is not present in our database.");
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			}
			return ResponseEntity.ok(this.employeeRepository.findById(id));

		} catch(Exception e) {
			LOGGER.error("Exception occured while fetching details", e);
		}

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

	}

	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateById(@RequestBody Employee employee, @PathVariable Integer id) {
		
		try {
			Employee updatedEmployee = this.employeeRepository.save(employee);
			return ResponseEntity.ok(updatedEmployee);
		} catch(Exception e) {
			LOGGER.error("Exception occured while updating the details.", e);
		}
				
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
		
	}
	
	
	@DeleteMapping("/delete/{id}")
	public String deleteById(@PathVariable Integer id) {
		try {
			if(employeeRepository.existsById(id)) {
				employeeRepository.deleteById(id);
				return "The entry is deleted successfully.";
			}
			else {
				return "No such entry is in our database.";
			}
		} catch(Exception e) {
			LOGGER.error("Exception occured while deleting the record.");
		}
		
		return "Error while deleting..";
	}
	
	
//	Pagination
//	Parameters which are used as attributes.
	@GetMapping("/getPageSortId")
	public Map<String, Object> getEmployeeByPageSortId(@RequestParam(name = "pageno", defaultValue = "0") int pageNo, 
			@RequestParam(name = "pagesize", defaultValue = "2") int pageSize,
			@RequestParam(name = "sortby", defaultValue = "id") String sortBy) {
		
		try {
			Map<String, Object> map = new HashMap<>();
			//Sorts the data using sortby argument.
			Sort sort = Sort.by(sortBy);
			//Pageable is an interface which hold a PageRequest.
			Pageable page = PageRequest.of(pageNo, pageSize, sort);
			//PageRequest is returning page not a List because it returns more information related to the page than only list.
			Page<Employee> employeePage = employeeRepository.findAll(page);
			map.put("data", employeePage.getContent());
			map.put("Total No. of Pages", employeePage.getTotalPages());
			map.put("Current Page", employeePage.getNumber());
			return map;
		} catch(Exception e) {
			LOGGER.error("Error while doing Pagination.", e);
		}
		
		return null;
	
	}
	
//	Pagination with Soriting on more than 1 attribute.
	@GetMapping("/getPageSortMultiple")
	public ResponseEntity<Page<Employee>> getEmployeesByPageMultipleAttribute(@RequestParam(name = "pageno", required = false, defaultValue = "0") int pageNo, 
	@RequestParam(name = "pagesize", required = false, defaultValue = "13") int pageSize,
	@RequestParam(name = "sortby", required = false, defaultValue = "id, asc") String sortBy[]) {
		
		final int defaultPage = 13;
		try {
			
			if(pageSize < 0 || pageSize > 13) {
				pageSize = defaultPage;
			}
			
			List<Order> orders = new ArrayList<Order>();
			if(sortBy.length > 2) {
				//if sorting is to be done using multiple fields. for eg -> [id,asc,name,desc]
				for(int i = 0; i < sortBy.length; i+=2) {
					orders.add(new Order(getSortDirection(sortBy[i+1]), sortBy[i]));
				}
			}
			else {
				orders.add(new Order(getSortDirection(sortBy[1]), sortBy[0]));
			}
			
//			Pageable is an interface and PageRequest is its child which imolements this interface.
			Pageable page = PageRequest.of(pageNo, pageSize, Sort.by(orders));
			//Returns a page so we need to create a page type variable.
			Page<Employee> emp = employeeRepository.findAll(page);
			return ResponseEntity.of(Optional.of(emp));
			
		} catch(Exception e) {
			LOGGER.error("Error occured while doing Pagination.", e);
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		
	}
	
	public Direction getSortDirection(String dir) {
		if(dir.equals("asc")) {
			return Sort.Direction.ASC;
		}
		if(dir.equals("desc")) {
			return Sort.Direction.DESC;
		}
		
		return null;
	}
	
//	Used for form information in frontend.
	@PatchMapping("/updateParticular/{id}")
	public ResponseEntity<?> updateParticular(@PathVariable int id, @RequestBody Employee employee) {
		try {
			Optional<Employee> emp = employeeRepository.findById(id);
			if(emp.isPresent()) {
				Employee em = emp.get();
				
				em.setName(employee.getName());
				em.setEmail(employee.getEmail());
				em.setPhone(employee.getPhone());
				em.setWebsite(employee.getWebsite());
				Employee final_em = employeeRepository.save(em);
				return ResponseEntity.ok(final_em);
			}
			else {
				ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		} catch(Exception e) {
			LOGGER.error("The entry which you are trying to update cannot be updated.", e);
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		
	}
	
	
} 
