package com.nethits.app.security.entity;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.persistence.*;

/*
 * Construye la base de datos
 * */

@Entity
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	@Column(unique=true)
	private String username;

	@NotNull
	private String password;
	
	@NotNull
	private String token;
	
	@NotNull
	private String token_expiration;
	
	@NotNull
	@ManyToMany
	@JoinTable(name="user_rol", joinColumns= @JoinColumn(name="user_id"), inverseJoinColumns = @JoinColumn(name="rol_id"))
	private Set<Rol> roles = new HashSet<>();

	public User() {
	}

	public User(@NotNull String username, @NotNull String password) {
		super();
		this.username = username;
		this.password = password;
		this.token = token;
		this.token_expiration = token_expiration;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getToken_expiration() {
		return token_expiration;
	}

	public void setToken_expiration(String token_expiration) {
		this.token_expiration = token_expiration;
	}

	public Set<Rol> getRoles() {
		return roles;
	}

	public void setRoles(Set<Rol> roles) {
		this.roles = roles;
	}

}
