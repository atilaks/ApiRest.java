package com.nethits.app.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nethits.app.security.entity.PrimaryUser;
import com.nethits.app.security.entity.User;

/*
 * Convierte la clase usuario en un usuario principal
 * */

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	@Autowired
	ServiceUser serviceUser;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = serviceUser.getByUserName(username).get();
		return PrimaryUser.build(user);
	}
}
