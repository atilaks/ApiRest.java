package com.nethits.app.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

import com.nethits.app.Message;
import com.nethits.app.security.dto.NewUser;
import com.nethits.app.security.entity.Rol;
import com.nethits.app.security.entity.User;
import com.nethits.app.security.jwt.JwtProvider;
import com.nethits.app.security.service.RolService;
import com.nethits.app.security.service.ServiceUser;
import com.nethits.app.security.dto.JwtDto;
import com.nethits.app.security.dto.UserLoging;
import com.nethits.app.security.enums.RolName;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired	
	AuthenticationManager authenticationManager;
	@Autowired		
	ServiceUser serviceUser;
	@Autowired	
	RolService rolService;
	@Autowired	
	JwtProvider jwtProvider;
	
	@PostMapping("/new")
	public ResponseEntity<?> nuevo (@Valid @RequestBody NewUser newUser, BindingResult bindingResult){
		if (bindingResult.hasErrors())
			return new ResponseEntity<>(new Message ("Misplaced fields"), HttpStatus.BAD_REQUEST);
//		if (ServiceUser.existByUserName(newUser.getUserName()))
//			return new ResponseEntity(new Message ("The name already exists"), HttpStatus.BAD_REQUEST);
		User user =
				new User(newUser.getUserName(), passwordEncoder.encode(newUser.getPassword()));
		Set<Rol> roles = new HashSet<>();
		roles.add(rolService.getByRolName(RolName.ROL_USER).get());
		if (newUser.getRoles().contains("admin"))
			roles.add(rolService.getByRolName(RolName.ROLE_ADMIN).get());
		user.setRoles(roles);
		serviceUser.save(user);
		return new ResponseEntity<>(new Message("Saved user"), HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login (@Valid @RequestBody UserLoging userLoging, BindingResult bindingResult){
		if (bindingResult.hasErrors())
			return new ResponseEntity<>(new Message ("Misplaced fields"), HttpStatus.BAD_REQUEST);
		Authentication authentication =
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoging.getUserName(), userLoging.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtProvider.generateToken(authentication);
		UserDetails userDetails = (UserDetails)authentication.getPrincipal();
		JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
		return new ResponseEntity<>(jwtDto, HttpStatus.OK);
	}
}











