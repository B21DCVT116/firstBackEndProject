package com.javaweb.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.help.generateToken;
import com.javaweb.model.Job;
import com.javaweb.model.UserCompany;
import com.javaweb.repository.UserCompanyRepository;
@RestController
public class CompanyController {

	private final UserCompanyRepository userCompanyRepository;

	public CompanyController(UserCompanyRepository userCompanyRepository) {
		this.userCompanyRepository = userCompanyRepository;
	}

//	@GetMapping("/loginCompany")
//	public Optional<UserCompany> getUserByEmailAndPassword(@RequestParam String email, @RequestParam String password) {
//		return userCompanyRepository.findByEmailAndPassword(email, password);
//	}
	
	//API đăng nhập
	@PostMapping("/loginCompany")
	public ResponseEntity<Optional<UserCompany>> login(@RequestBody UserCompany loginRequest) {
	    Optional<UserCompany> user = userCompanyRepository.findByEmailAndPassword(
	        loginRequest.getEmail(), 
	        loginRequest.getPassword()
	    );
	    if (user != null) {
	    	System.out.print(loginRequest.getEmail());
	        return ResponseEntity.ok(user);
	     }
	    return null;
	}
	
	//API đăng ký tài khoản
	@PostMapping("/logupCompany")
	public ResponseEntity<String> registerCompany(@RequestBody UserCompany userCompany) {
		// Kiểm tra email đã tồn tại chưa
		if (userCompanyRepository.findByEmail(userCompany.getEmail()).isPresent()) {
			return ResponseEntity.badRequest().body("Email đã tồn tại!");
		}
		userCompany.setToken(generateToken.generateRandomString(24));
		// Lưu vào database
		userCompanyRepository.save(userCompany);
		return ResponseEntity.ok("Đăng ký thành công!");
	}
	// API cập nhật một phần thông tin công ty
    @PatchMapping("/updateCompany/{id}") 
    public ResponseEntity<String> updateCompanyPartial(@PathVariable Long id, @RequestBody UserCompany updatedCompany) {
        Optional<UserCompany> existingCompanyOpt = userCompanyRepository.findById(id);

        if (existingCompanyOpt.isPresent()) {
            UserCompany company = existingCompanyOpt.get();

            // Kiểm tra từng field trong request có null không, nếu không thì cập nhật
            if (updatedCompany.getName() != null) {
                company.setName(updatedCompany.getName());
            }
            if (updatedCompany.getEmail() != null) {
                company.setEmail(updatedCompany.getEmail());
            }
            if (updatedCompany.getPassword() != null) {
                company.setPassword(updatedCompany.getPassword());
            }
            if (updatedCompany.getPhoneCompany() != null) {
                company.setPhoneCompany(updatedCompany.getPhoneCompany());
            }
            if (updatedCompany.getAddressCompany() != null) {
                company.setAddressCompany(updatedCompany.getAddressCompany());
            }
            if (updatedCompany.getWebsite() != null) {
                company.setWebsite(updatedCompany.getWebsite());
            }
            if (updatedCompany.getDescription() != null) {
                company.setDescription(updatedCompany.getDescription());
            }
            if (updatedCompany.getWorkTime() != null) {
                company.setWorkTime(updatedCompany.getWorkTime());
            }
            if (updatedCompany.getQuantityPeople()!=null) {
                company.setQuantityPeople(updatedCompany.getQuantityPeople());
            }
            if (updatedCompany.getDetail() != null) {
                company.setDetail(updatedCompany.getDetail());
            }

            userCompanyRepository.save(company); // Lưu vào database
            return ResponseEntity.ok("Cập nhật công ty thành công!");
        } else {
            return ResponseEntity.badRequest().body("Không tìm thấy công ty với ID: " + id);
        }
    }
    //Lấy tất cả công ty
    @GetMapping("/getAllCompany")
    public ResponseEntity<List<UserCompany>> getAllCompany() {
        List<UserCompany> company = userCompanyRepository.findAll();
        if (!company.isEmpty()) {
            return ResponseEntity.ok(company);
        }
        return ResponseEntity.notFound().build();
    }
    
   //Lấy thông tin chi tiết công ty
    @GetMapping("/getDetailCompany/{id}")
    public ResponseEntity<Optional<UserCompany>> getDetialCompany(@PathVariable Long id) {
        Optional<UserCompany> company = userCompanyRepository.findById(id);
        if (!company.isEmpty()) {
            return ResponseEntity.ok(company);
        }
        return ResponseEntity.notFound().build();
    }
	
}
