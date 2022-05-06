package com.nethits.app.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nethits.app.Message;
import com.nethits.app.dto.CustomerDto;
import com.nethits.app.entity.Customer;
import com.nethits.app.service.CustomerService;

@RestController
@RequestMapping("/customer")
@CrossOrigin
public class CustomerController {
	@Autowired
	CustomerService customerService;
	
	@GetMapping("/list")
	public ResponseEntity<List<Customer>> list(){
		List<Customer> list = customerService.list();
		return new ResponseEntity<List<Customer>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/detail/{id}")
	public ResponseEntity<Customer> getById(@PathVariable("id") int id){
		if(!customerService.existById(id))
			return new ResponseEntity(new Message("Does not exist"), HttpStatus.NOT_FOUND);
		Customer customer = customerService.getOne(id).get();
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}
	
	@GetMapping("/detailname/{name}")
	public ResponseEntity<Customer> getByName(@PathVariable("name") String name){
		if(!customerService.existByName(name))
			return new ResponseEntity(new Message("Does not exist"), HttpStatus.NOT_FOUND);
		Customer customer = customerService.getByName(name).get();
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody CustomerDto customerDto){
		if(StringUtils.isBlank(customerDto.getName()))
			return new ResponseEntity<>(new Message("Need name"), HttpStatus.BAD_REQUEST);
		if(customerService.existByName(customerDto.getName()))
			return new ResponseEntity<>(new Message("This name is duplicated"), HttpStatus.BAD_REQUEST);
		Customer customer = new Customer(customerDto.getName(), customerDto.getPhone());
		customerService.save(customer);
		return new ResponseEntity<>(new Message("Customer created"), HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@PathVariable("id")int id, @RequestBody CustomerDto customerDto){
		if(!customerService.existById(id))
			return new ResponseEntity<>(new Message("Does not exist"), HttpStatus.NOT_FOUND);
		if(customerService.existByName(customerDto.getName()) && customerService.getByName(customerDto.getName()).get().getId() != id)
			return new ResponseEntity<>(new Message("This name is duplicated"), HttpStatus.BAD_REQUEST);
		if(StringUtils.isBlank(customerDto.getName()))
			return new ResponseEntity<>(new Message("Need name"), HttpStatus.BAD_REQUEST);
		Customer customer = customerService.getOne(id).get();
		customer.setName(customerDto.getName());
		customer.setPhone(customerDto.getPhone());
		customerService.save(customer);
		return new ResponseEntity<>(new Message("Customer updated"), HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id")int id){
		if(!customerService.existById(id))
			return new ResponseEntity<>(new Message("Does not exist"), HttpStatus.NOT_FOUND);
		customerService.delete(id);
		return new ResponseEntity<>(new Message("Customer deleted"), HttpStatus.OK);
	}
}
