package com.nethits.app.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nethits.app.security.entity.User;

@Repository
public interface RepositoryUser extends JpaRepository<User, Integer> {
	Optional<User> findByUserName(String userName);
	boolean existByUserName(String userName);
}
