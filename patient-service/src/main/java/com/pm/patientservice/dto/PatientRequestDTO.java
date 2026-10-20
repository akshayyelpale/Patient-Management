/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.validation.constraints.Email
 *  jakarta.validation.constraints.NotBlank
 *  jakarta.validation.constraints.Size
 */
package com.pm.patientservice.dto;

import com.pm.patientservice.dto.validators.CreatePatientValidationGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PatientRequestDTO {
    @NotBlank(message="Name is required")
    @Size(max=100, message="Name cannot exceed 100 characters")
    private @NotBlank(message="Name is required") @Size(max=100, message="Name cannot exceed 100 characters") String name;
    @NotBlank(message="Email is Required")
    @Email(message="Email Should Be Valid")
    private @NotBlank(message="Email is Required") @Email(message="Email Should Be Valid") String email;
    @NotBlank(message="Address is required")
    private @NotBlank(message="Address is required") String address;
    @NotBlank(message="DOB is Required")
    private @NotBlank(message="DOB is Required") String dateOfBirth;
    @NotBlank(groups={CreatePatientValidationGroup.class}, message="Registred date is required")
    private @NotBlank(groups={CreatePatientValidationGroup.class}, message="Registred date is required") String registrationDate;

    public String getName() {
        return this.name;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getRegistrationDate() {
        return this.registrationDate;
    }

    public void registrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }
}
