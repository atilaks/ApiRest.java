package com.nethits.app.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nethits.app.entity.Customer;
import com.nethits.app.repository.CustomerRepository;

@Service
@Transactional
public class CustomerService {
	@Autowired
	CustomerRepository customerRepository;
	
	public List<Customer> list(){
		return customerRepository.findAll();
	}
	
	public Optional<Customer> getOne(int id){
		return customerRepository.findById(id);
	}
	
	public Optional<Customer> getByName(String name){
		return customerRepository.findByName(name);
	}
	
	public void save(Customer customer) {
		customerRepository.save(customer);
	}
	
	public void delete(int id) {
		customerRepository.deleteById(id);
	}
	
	public boolean existById(int id) {
		return customerRepository.existsById(id);
	}
	
	public boolean existByName(String name) {
		return customerRepository.existByName(name);
	}
}
