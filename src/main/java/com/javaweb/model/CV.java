package com.javaweb.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CV")

public class CV {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

    @ElementCollection
    @CollectionTable(name = "cv_cities", joinColumns = @JoinColumn(name = "cv_id"))
    @Column(name = "city")
    private List<String> city;
    
    private String nameCandidate;
    
    private String phone;

    public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getNameCandidate() {
		return nameCandidate;
	}

	public void setNameCandidate(String nameCandidate) {
		this.nameCandidate = nameCandidate;
	}

	private LocalDateTime createdAt;

    private String description;

    private String email;
    
    private boolean statusRead;

    public boolean getStatus() {
		return statusRead;
	}

	// Quan hệ với Company
    @ManyToOne
    @JoinColumn(name = "id_company", referencedColumnName = "id", nullable = false)
    private UserCompany company;

    // Quan hệ với Job
    @ManyToOne
    @JoinColumn(name = "id_job", referencedColumnName = "id", nullable = false)
    private Job job;

    @ElementCollection
    @CollectionTable(name = "cv_link_projects", joinColumns = @JoinColumn(name = "cv_id"))
    @Column(name = "link_project")
    private List<String> linkProject;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<String> getCity() {
		return city;
	}

	public void setCity(List<String> city) {
		this.city = city;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserCompany getCompany() {
		return company;
	}

	public void setCompany(UserCompany company) {
		this.company = company;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public List<String> getLinkProject() {
		return linkProject;
	}

	public void setLinkProject(List<String> linkProject) {
		this.linkProject = linkProject;
	}

	public void setStatus(boolean status) {
		this.statusRead = status;
	} 
    

}
