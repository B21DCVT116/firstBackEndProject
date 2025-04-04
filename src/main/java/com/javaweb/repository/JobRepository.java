package com.javaweb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.javaweb.model.Job;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByCompanyId(Long companyId);
    Optional<Job> findById(Long Id);
    
//    @Query("""
//            SELECT j FROM Job j
//            LEFT JOIN j.city c
//            LEFT JOIN j.tag t
//            WHERE 
//                (:name IS NULL OR j.name LIKE %:name%)
//                AND (:tag IS NULL OR t IN :tag)
//                AND (:city IS NULL OR c IN :city)
//                AND (:minSalary IS NULL OR j.salary >= :minSalary)
//                AND j.status = true
//        """)
//    
//    @Query("""
//            SELECT j FROM Job j
//            WHERE 
//                (:name IS NULL OR j.name LIKE %:name%)
//                AND (:tags IS NULL OR EXISTS (
//                    SELECT t FROM j.tag t WHERE t IN :tags
//                ))
//                AND (:cities IS NULL OR EXISTS (
//                    SELECT c FROM j.city c WHERE c IN :cities
//                ))
//                AND (:minSalary IS NULL OR j.salary >= :minSalary)
//                AND j.status = true
//        """)
    @Query("""
            SELECT j FROM Job j
            WHERE 
                (:name IS NULL OR j.name LIKE %:name%)
                AND (COALESCE(:tags, NULL) IS NULL OR EXISTS (
                    SELECT t FROM j.tag t WHERE t IN :tags
                ))
                AND (COALESCE(:cities, NULL) IS NULL OR EXISTS (
                    SELECT c FROM j.city c WHERE c IN :cities
                ))
                AND (:minSalary IS NULL OR j.salary >= :minSalary)
                AND j.status = true
        """)
    
    List<Job> searchJobs(
            @Param("name") String name,
            @Param("tags") List<String> tags,
            @Param("cities") List<String> cities,
            @Param("minSalary") Double minSalary
        );
    
    
    
    
    @Query("SELECT DISTINCT c FROM Job j JOIN j.city c")
    List<String> findAllDistinctCities();
    
    @Query("SELECT DISTINCT t FROM Job j JOIN j.tag t")
    List<String> findAllDistinctTags();
}