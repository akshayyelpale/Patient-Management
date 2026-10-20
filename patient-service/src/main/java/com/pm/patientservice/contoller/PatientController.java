
package com.pm.patientservice.contoller;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.dto.validators.CreatePatientValidationGroup;
import com.pm.patientservice.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

@RestController
@RequestMapping(value={"/akshay"})
@Tag(name="Patient", description="API for managing Patients")
@CrossOrigin(origins={"*"})
public class PatientController {
    private final PatientService patientservice;

    public PatientController(PatientService patientService) {
        this.patientservice = patientService;
    }

    @GetMapping(value={"/patients"})
    @Operation(summary="Get Patients")
    public ResponseEntity<List<PatientResponseDTO>> getPatients() {
        List<PatientResponseDTO> patients = this.patientservice.getpatients();
        return ResponseEntity.ok().body(patients);
    }

    @PostMapping(value={"/addpatients"})
    @Operation(summary="Create a new patient")
    public ResponseEntity<PatientResponseDTO> createPatient(@Validated(value={Default.class, CreatePatientValidationGroup.class}) @RequestBody PatientRequestDTO PRdto) {
        PatientResponseDTO patientResponseDTO = this.patientservice.createPatient(PRdto);
        return ResponseEntity.ok(patientResponseDTO);
    }

    @PutMapping(value={"update/{id}"})
    @Operation(summary="Update the patient")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable int id, @Validated(value={Default.class}) @RequestBody PatientRequestDTO dto) {
        PatientResponseDTO patientResponseDTO = this.patientservice.updatePatient(id, dto);
        return ResponseEntity.ok(patientResponseDTO);
    }

    @DeleteMapping(value={"/delete/{id}"})
    @Operation(summary="Delete a patient")
    public ResponseEntity<String> deletePatient(@PathVariable int id) {
        try {
            this.patientservice.deletePatient(id);
            return ResponseEntity.ok("Patient deleted successfully");
        }
        catch (RuntimeException e) {
            return ResponseEntity.status((HttpStatusCode)HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
