package com.nethits.app.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nethits.app.security.entity.User;
import com.nethits.app.security.repository.RepositoryUser;

@Service
@Transactional
public class ServiceUser {
	@Autowired
	RepositoryUser repositoryUser;
	
	public Optional<User> getByUserName(String userName){
		return repositoryUser.findByUserName(userName);
	}
	public boolean existByUserName(String userName) {
		return repositoryUser.existByUserName(userName);
	}
	public void save(User user) {
		repositoryUser.save(user);
	}
}
