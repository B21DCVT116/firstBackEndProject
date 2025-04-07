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

import com.javaweb.model.CV;
import com.javaweb.model.Job;
import com.javaweb.repository.CVRepository;
import com.javaweb.repository.JobRepository;

@RestController
public class CVController {
	private final CVRepository cvRepository;
	private final JobRepository jobRepository;
	
	public CVController (CVRepository cvRepository, JobRepository jobRepository) {
		this.cvRepository = cvRepository;
		this.jobRepository = jobRepository;
	}
	
	//Phần cho company
	//Lấy tất cả CV của 1 công ty
	@GetMapping("/company/getAllCV")
    public ResponseEntity<List<CV>> getCVByCompanyId(
    		@RequestParam (value = "idCompany", required = true) Long companyId) {
        List<CV> CV = cvRepository.findByCompanyId(companyId);
        if (!CV.isEmpty()) {
            return ResponseEntity.ok(CV);
        }
        return ResponseEntity.notFound().build();
    }
	
	//Lấy chi tiết 1 CV
	@GetMapping("/company/getDetailCV")
    public ResponseEntity<CV> getDetailCV(@RequestParam (value = "idCV", required = true) Long idCV) {
        Optional<CV> CV = cvRepository.findById(idCV);
        if (!CV.isEmpty()) {
            return ResponseEntity.ok(CV.get());
        }
        return ResponseEntity.notFound().build();
    }
	
	//Cập nhật trạng thái của CV
	@PatchMapping ("/company/changeStatus") 
    public ResponseEntity<CV> changeCV(
    		@RequestParam(value = "id", required = true) Long id){
    	Optional<CV> existingCV = cvRepository.findById(id);
    	
    	if (existingCV.isPresent()) {
            CV cv = existingCV.get();
            cv.setStatus(true);
            cvRepository.save(cv); // Lưu vào database
            return ResponseEntity.ok(cv);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }
	
	//Xóa CV
	@DeleteMapping("/company/deleteCV")
    public ResponseEntity<String> deleteCV(@RequestParam(value = "id", required = true) Long id){
    	Optional<CV> cv = cvRepository.findById(id);
    	if(cv.isPresent()) {
    		cvRepository.delete(cv.get());
    		return ResponseEntity.ok("Xóa cv thành công!");
    	}
    	else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy cv với ID: " + id);
        }
    }
	
    //thêm mới một cv
    @PostMapping("/candidate/createCV")
    public ResponseEntity<String> createCV(
    		@RequestParam(value = "idJob", required = true) Long idJob ,
    		@RequestBody CV cv
    		){
    	Optional<Job> job = jobRepository.findById(idJob);   
    	CV newCV = new CV();
    	newCV.setJob(job.get());
    	newCV.setNameCandidate(cv.getNameCandidate());
    	newCV.setCity(cv.getCity());
    	newCV.setCompany(job.get().getCompany());
    	newCV.setEmail(cv.getEmail());
    	newCV.setDescription(cv.getDescription());
    	newCV.setLinkProject(cv.getLinkProject());
    	newCV.setStatus(false);
    	newCV.setPhone(cv.getPhone());
    	newCV.setCreatedAt(LocalDateTime.now());

    	cvRepository.save(newCV);
		return ResponseEntity.ok("Ứng tuyển thành công!");
    }
	
}
