package com.hexaware.springJPA.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.springJPA.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

	public UserEntity findByUsername(String username);
}
