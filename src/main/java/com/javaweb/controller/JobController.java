package com.javaweb.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.model.Job;
import com.javaweb.model.UserCompany;
import com.javaweb.repository.JobRepository;
import com.javaweb.repository.UserCompanyRepository;

@RestController
public class JobController {

    private final JobRepository jobRepository;
    private final UserCompanyRepository userCompanyRepository;

    public JobController(JobRepository jobRepository, UserCompanyRepository userCompanyRepository) {
        this.jobRepository = jobRepository;
        this.userCompanyRepository = userCompanyRepository;
    }
    
    //Phần cho Company
    
    //Lấy tất cả job của 1 công ty
    @GetMapping("/company/getAllJobCompany")
    public ResponseEntity<List<Job>> getJobsByCompanyId(@RequestParam(value = "idCompany", required = true) Long companyId) {
        List<Job> jobs = jobRepository.findByCompanyId(companyId);
        if (!jobs.isEmpty()) {
            return ResponseEntity.ok(jobs);
        }
        return ResponseEntity.notFound().build();
    }
    
    //Lấy chi tiết 1 job 
    @GetMapping("/company/getDetailJob")
    public ResponseEntity<Job> getJobsDetail(@RequestParam(value = "idJob", required = true) Long idJob) {
        Optional<Job> jobs = jobRepository.findById(idJob);
        if (!jobs.isEmpty()) {
            return ResponseEntity.ok(jobs.get());
        }
        return ResponseEntity.notFound().build();
    }
    
    //thêm mới một job
    @PostMapping("/company/createjob")
    public ResponseEntity<String> createJob(
    		@RequestParam(value = "idCompany", required = true) Long idCompany , 
    		@RequestBody Job job
    		){
    	Optional<UserCompany> company = userCompanyRepository.findById(idCompany);
    	Job newJob = new Job();
    	newJob.setCompany(company.get());
        newJob.setCity(job.getCity());
        newJob.setTag(job.getTag());
        newJob.setDescription(job.getDescription());
        newJob.setName(job.getName());
        newJob.setSalary(job.getSalary());
        newJob.setStatus(job.getStatus() != null ? job.getStatus() : true);
        newJob.setCreatedAt(LocalDateTime.now());
        newJob.setUpdatedAt(LocalDateTime.now());

    	jobRepository.save(newJob);
		return ResponseEntity.ok("Thêm công việc thành công!");
    }
    
   
    
    //chỉnh sửa 1 job công ty
    @PatchMapping ("/company/updatejob") 
    public ResponseEntity<String> updateJob(
    		@RequestParam(value = "id", required = true) Long id ,
    		@RequestBody Job updatedJob
    		){
    	Optional<Job> existingJob = jobRepository.findById(id);
    	
    	if (existingJob.isPresent()) {
            Job job = existingJob.get();

            // Kiểm tra từng field trong request có null không, nếu không thì cập nhật
            if (updatedJob.getName() != null) {
                job.setName(updatedJob.getName());
            }
            if (updatedJob.getSalary() != null) {
                job.setSalary(updatedJob.getSalary());
            }
            if (updatedJob.getStatus() != null) {
                job.setStatus(updatedJob.getStatus());
            }
            if (updatedJob.getUpdatedAt() != null) {
                job.setUpdatedAt(LocalDateTime.now());
            }
            if (updatedJob.getCity() != null) {
                job.setCity(updatedJob.getCity());
            }
            if (updatedJob.getDescription() != null) {
                job.setDescription(updatedJob.getDescription());
            }
            if (updatedJob.getTag() != null) {
                job.setTag(updatedJob.getTag());
            }
            job.setUpdatedAt(LocalDateTime.now());

            jobRepository.save(job); // Lưu vào database
            return ResponseEntity.ok("Cập nhật công việc thành công!");
        } else {
            return ResponseEntity.badRequest().body("Không tìm thấy công ty với ID: " + id);
        }
    }
    
    //xóa 1 công việc
    @DeleteMapping("/company/deletejob")
    public ResponseEntity<String> deleteJob(@RequestParam(value = "id", required = true) Long id){
    	Optional<Job> job = jobRepository.findById(id);
    	if(job.isPresent()) {
    		jobRepository.delete(job.get());
    		return ResponseEntity.ok("Xóa công việc thành công!");
    	}
    	else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy công việc với ID: " + id);
        }
    }
    
    //Phần cho candidate
  //search job 
    @GetMapping("/company/searchJob")
    public ResponseEntity<List<Job>> searchJobs(
    	    @RequestParam(value = "name", required = false) String name,
    	    @RequestParam(value = "tag", required = false) List<String> tags,
    	    @RequestParam(value = "city", required = false) List<String> cities,
    	    @RequestParam(value = "minSalary", required = false) Double minSalary
    	) {
    	    List<Job> jobs = jobRepository.searchJobs(name, tags, cities, minSalary);
    	    return jobs.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(jobs);
    	}
    
    
    
    
    @GetMapping("/getListTag")
    public ResponseEntity<List<String>> getListTag() {
    	    List<String> tag = jobRepository.findAllDistinctTags();
    	    return tag.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(tag);
    	}
    @GetMapping("/getListCity")
    public ResponseEntity<List<String>> getListCity() {
    	    List<String> city = jobRepository.findAllDistinctCities();
    	    return city.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(city);
    	}
}