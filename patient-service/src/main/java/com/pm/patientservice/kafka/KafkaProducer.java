package com.pm.patientservice.kafka;

import com.pm.patientservice.model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import patient.events.PatientEvent;

@Service
public class KafkaProducer {

    private static final Logger log =
            LoggerFactory.getLogger(KafkaProducer.class);

    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(Patient patient) {

        log.info("========== KAFKA PRODUCER START ==========");

        PatientEvent event = PatientEvent.newBuilder()
                .setPatientId(String.valueOf(patient.getId()))
                .setName(patient.getName())
                .setEmail(patient.getEmail())
                .setEventType("PATIENT_CREATED")
                .build();

        log.info("Creating Patient Event");
        log.info("Patient Id: {}", patient.getId());
        log.info("Patient Name: {}", patient.getName());
        log.info("Patient Email: {}", patient.getEmail());

        try {

            log.info("Sending event to Kafka topic: patient");

            kafkaTemplate.send("patient", event.toByteArray());

            log.info("Patient Event sent successfully");
            log.info("Event Type: {}", event.getEventType());
            log.info("========== KAFKA PRODUCER END ==========");

        } catch (Exception e) {

            log.error("Error sending Kafka event", e);
            log.error("Failed Event: {}", event);
        }
    }
}