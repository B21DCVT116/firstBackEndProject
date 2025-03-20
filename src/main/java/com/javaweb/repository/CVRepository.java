package com.javaweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaweb.model.CV;

public interface CVRepository extends JpaRepository<CV, Long> {
	List<CV> findByCompanyId(Long companyId);
}
