package com.pm.patientservice.dto;

import java.util.Date;

import com.pm.patientservice.dto.validators.CreatePatientValidationGroup;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PatientRequestDTO {
	
	@NotBlank(message = "Name is required")
	@Size(max=100, message = "Name cannot exceed 100 characters")
	private String name;
	
	@NotBlank(message = "Email is Required")
	@Email(message = "Email Should Be Valid")
	private String email;
	
	@NotBlank(message= "Address is required")
	private String address;
	
	@NotBlank(message = "DOB is Required")
	private String dateOfBirth;
	
	@NotBlank(groups =  CreatePatientValidationGroup.class, message = "Registred date is required")
	private String registrationDate;

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getRegistrationDate() {
		return registrationDate;
	}

	public void registrationDate ( String registrationDate) {
		this.registrationDate = registrationDate;
	}
	 
	
	
	
	 
	
}
