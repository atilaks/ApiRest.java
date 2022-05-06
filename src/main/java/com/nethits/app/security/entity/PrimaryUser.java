package com.nethits.app.security.entity;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/*
 * Usuario con privilegios
 * */

public class PrimaryUser implements UserDetails {
	private static final long serialVersionUID = -1481313165701007102L;
	private String username;
	private String password;
	private String token;
	private String token_expiration;
	private Collection<? extends GrantedAuthority> authorities;
	
	

	public PrimaryUser(String username, String password, String token, String token_expiration,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.username = username;
		this.password = password;
		this.token = token;
		this.token_expiration = token_expiration;
		this.authorities = authorities;
	}

	public static PrimaryUser build(User user) {
		List<GrantedAuthority> authorities =
				user.getRoles().stream().map(rol -> new SimpleGrantedAuthority(rol.getRolName().name())).collect(Collectors.toList());
		return new PrimaryUser(user.getUsername(), user.getPassword(), user.getToken(), user.getToken_expiration(), authorities);
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
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

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	
}
