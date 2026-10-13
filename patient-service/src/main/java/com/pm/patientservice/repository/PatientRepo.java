package com.pm.patientservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.pm.patientservice.model.Patient;

@Repository
public interface PatientRepo extends JpaRepository<Patient, Integer> {
	
	boolean existsByEmail(String email);
	
	boolean existsByEmailAndIdNot (String email, int Id);
}
