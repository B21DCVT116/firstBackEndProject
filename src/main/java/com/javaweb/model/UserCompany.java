package com.javaweb.model;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "UserCompany")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)  // Thêm annotation này
@DiscriminatorColumn(name = "dtype")                   // Xác định tên cột discriminator
@DiscriminatorValue("Company")                         
public class UserCompany {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String name;

	private String email;

	private String password;

	private String phoneCompany;

	private String addressCompany;

	private String website;

	private String description;

	private String workTime;
	
	private String quantityPeople;
	
	private String detail;
	
	public String getQuantityPeople() {
		return quantityPeople;
	}

	public void setQuantityPeople(String quantityPeople) {
		this.quantityPeople = quantityPeople;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	private String token;
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setAddressCompany(String addressCompany) {
		this.addressCompany = addressCompany;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneCompany() {
		return phoneCompany;
	}

	public void setPhoneCompany(String phoneCompany) {
		this.phoneCompany = phoneCompany;
	}

	public String getAddressCompany() {
		return addressCompany;
	}

	public void setAddressCompany(Object object) {
		this.addressCompany = (String) object;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getWorkTime() {
		return workTime;
	}

	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}
}
