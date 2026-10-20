/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.stereotype.Service
 */
package com.pm.patientservice.service;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.exception.EmailAlreadyExistsException;
import com.pm.patientservice.exception.PatientNotFoundException;
import com.pm.patientservice.grpc.BillingServiceGrpcClient;
import com.pm.patientservice.kafka.KafkaProducer;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepo;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PatientService {
    private PatientRepo patientrepo;
    private BillingServiceGrpcClient billingServiceGrpcClient;
    private KafkaProducer kafkaProducer;

    public PatientService(PatientRepo patientrepo, BillingServiceGrpcClient billingServiceGrpcClient, KafkaProducer kafkaProducer) {
        this.patientrepo = patientrepo;
        this.billingServiceGrpcClient = billingServiceGrpcClient;
        this.kafkaProducer = kafkaProducer;
    }

    public List<PatientResponseDTO> getpatients() {
        List<Patient> patients = this.patientrepo.findAll();

        return patients.stream()
                .map(patient -> PatientMapper.toDTO(patient))
                .toList();
    }
    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        if (this.patientrepo.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("A patient with this email already exists " + patientRequestDTO.getEmail());
        }
        Patient newPatient = (Patient)this.patientrepo.save(PatientMapper.toModel(patientRequestDTO));
        this.billingServiceGrpcClient.createBillingAccount(String.valueOf(newPatient.getId()), newPatient.getName(), newPatient.getEmail());
        System.out.println("Calling Kafka Producer...");
        this.kafkaProducer.sendEvent(newPatient);
        return PatientMapper.toDTO(newPatient);
    }

    public PatientResponseDTO updatePatient(int id, PatientRequestDTO patientRequestDTO) {
        Patient patient = (Patient)this.patientrepo.findById(id).orElseThrow(() -> new PatientNotFoundException("Patient not found with this id: " + id));
        if (this.patientrepo.existsByEmailAndIdNot(patientRequestDTO.getEmail(), id)) {
            throw new EmailAlreadyExistsException("A patient with this email already exists " + patientRequestDTO.getEmail() + id);
        }
        patient.setName(patientRequestDTO.getName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        Patient updatedPatient = (Patient)this.patientrepo.save(patient);
        return PatientMapper.toDTO(updatedPatient);
    }

    public void deletePatient(int id) {
        Patient patient = (Patient)this.patientrepo.findById(id).orElseThrow(() -> new RuntimeException("No patient found with id: " + id));
        this.patientrepo.delete(patient);
    }
}
