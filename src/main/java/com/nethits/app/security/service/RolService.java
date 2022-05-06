package com.nethits.app.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nethits.app.security.entity.Rol;
import com.nethits.app.security.enums.RolName;
import com.nethits.app.security.repository.RolRepository;

@Service
@Transactional
public class RolService {
	@Autowired
	RolRepository rolRepository;
	
	public Optional<Rol> getByRolName(RolName rolName){
		return rolRepository.findByRolName(rolName);
	}
}
