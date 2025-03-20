package com.javaweb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaweb.model.UserCompany;

public interface UserCompanyRepository extends JpaRepository<UserCompany, Long>{
	Optional<UserCompany> findByEmailAndPassword(String email, String password);
	Optional<UserCompany> findByEmail(String email);
}
