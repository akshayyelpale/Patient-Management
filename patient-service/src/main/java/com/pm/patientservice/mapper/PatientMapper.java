package com.pm.patientservice.mapper;

import java.time.LocalDate;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.model.Patient;

public class PatientMapper {

    public static PatientResponseDTO toDTO(Patient patient) {
        PatientResponseDTO patientDTO = new PatientResponseDTO();
        patientDTO.setId(patient.getId());
        patientDTO.setName(patient.getName());
        patientDTO.setAddress(patient.getAddress().toString());
        patientDTO.setEmail(patient.getEmail().toString());
        patientDTO.setDateOfBirth(patient.getDateOfBirth().toString());
        patientDTO.setRegistrationDate(patient.getRegistrationDate().toString());
        return patientDTO;
    }
    
    public static Patient toModel(PatientRequestDTO patientRequestDTO) {
        Patient patient = new Patient();
        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        patient.setRegistrationDate(LocalDate.parse(patientRequestDTO.getRegistrationDate()));
        return patient;
    }
    
}
