package com.pm.patientservice.contoller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.dto.validators.CreatePatientValidationGroup;
import com.pm.patientservice.service.PatientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;


@RestController
@RequestMapping("/akshay")
@Tag(name = "Patient" , description = "API for managing Patients")
@CrossOrigin(origins = "*")
public class PatientController {
	
	private final PatientService patientservice;
	
	public PatientController(PatientService patientService) {
		
		this.patientservice =patientService;
	}
	
	@GetMapping("/patients")
	@Operation (summary = "Get Patients")
	public ResponseEntity<List<PatientResponseDTO>> getPatients(){
		
		List<PatientResponseDTO> patients= patientservice.getpatients();
		return ResponseEntity.ok().body(patients);
		
	}
	
	@PostMapping("/addpatients")
	@Operation (summary = "Create a new patient")
	public ResponseEntity<PatientResponseDTO> createPatient(@Validated({Default.class, CreatePatientValidationGroup.class}) @RequestBody PatientRequestDTO PRdto  ){
		 PatientResponseDTO patientResponseDTO = patientservice.createPatient(PRdto);
		return ResponseEntity.ok().body(patientResponseDTO);
		
	}
	
	@PutMapping("update/{id}")
	@Operation (summary = "Update the patient")
	public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable int id,@Validated ({Default.class}) @RequestBody PatientRequestDTO dto){
		
		PatientResponseDTO patientResponseDTO= patientservice.updatePatient(id, dto);
		
		return ResponseEntity.ok().body(patientResponseDTO);
	}
	
	@DeleteMapping("/delete/{id}")
	@Operation (summary = "Delete a patient")
	public ResponseEntity<String> deletePatient(@PathVariable int id) {
	    try {
	        patientservice.deletePatient(id);
	        return ResponseEntity.ok("Patient deleted successfully");
	    } catch (RuntimeException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body(e.getMessage());
	    }
	}

	{}
}
