package com.nethits.app.security.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.nethits.app.security.entity.PrimaryUser;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/*
 * Genera el token y lo valida
 * */

@Component
public class JwtProvider {
	private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);
	
	@Value("${jwt.secret}")
	private String secret;
	@Value("${jwt.expiration}")
	private int expiration;
	
	public String generateToken(Authentication authentication) {
		PrimaryUser primaryUser = (PrimaryUser) authentication.getPrincipal();
		
		return Jwts.builder().setSubject(primaryUser.getUsername())
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + expiration))
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();
	}
	public String getUserNameFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
	}
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			return true;
		}catch (MalformedJwtException e) {
			logger.error("Token build error");		
		}catch (UnsupportedJwtException e) {
			logger.error("Token not supported");		
		}catch (ExpiredJwtException e) {
			logger.error("Token expired");		
		}catch (IllegalArgumentException e) {
			logger.error("Token empty");		
		}catch (SignatureException e) {
			logger.error("Error on Signature");		
		}
		return false;

	}
}
