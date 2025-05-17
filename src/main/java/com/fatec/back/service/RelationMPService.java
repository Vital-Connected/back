package com.fatec.back.service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.back.domain.Medication.Medication;
import com.fatec.back.domain.Patient.Patient;
import com.fatec.back.domain.RelationMP.RelationMP;
import com.fatec.back.domain.RelationMP.RelationMPDTO;
import com.fatec.back.domain.User.User;
import com.fatec.back.repository.MedicationRepository;
import com.fatec.back.repository.PatientRepository;
import com.fatec.back.repository.RelationMPRepository;
import com.fatec.back.repository.UserRepository;

@Service
public class RelationMPService {
    @Autowired
    private RelationMPRepository relationMPRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MedicationRepository medicationRepository;

    public List<RelationMP> getAllRelationMPs(){
        return relationMPRepository.findAll();
    }

    public Optional<RelationMP> getRelationMPById(Long id){
        return relationMPRepository.findById(id);
    }

    public RelationMP createRelationMP(RelationMPDTO dto) {
        User user = userRepository.findById(dto.userId())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Patient patient = patientRepository.findById(dto.patient())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Medication medication = medicationRepository.findById(dto.medication())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        RelationMP relationMP = new RelationMP();

        long totalUnits = switch (dto.frequencyUnit()) {
        case HOURS -> Duration.between(dto.startDate().atStartOfDay(), dto.endDate().plusDays(1).atStartOfDay()).toHours() / dto.frequencyValue();
        case DAYS -> ChronoUnit.DAYS.between(dto.startDate(), dto.endDate().plusDays(1)) / dto.frequencyValue();
        case WEEKS -> ChronoUnit.WEEKS.between(dto.startDate(), dto.endDate().plusDays(1)) / dto.frequencyValue();
        };

        int totalDosage = (int) totalUnits * dto.dosage();
        relationMP.setStartDate(dto.startDate());
        relationMP.setEndDate(dto.endDate());
        relationMP.setDosage(dto.dosage());
        relationMP.setFrequencyValue(dto.frequencyValue());
        relationMP.setFrequencyUnit(dto.frequencyUnit());
        relationMP.setMedication(medication);
        relationMP.setPatient(patient);
        relationMP.setCreatedBy(user);
        relationMP.setUpdatedBy(user);
        relationMP.setTotalDosage(totalDosage);

        return relationMPRepository.save(relationMP);
    }

   public Optional<RelationMP> updateRelationMP(Long id, RelationMPDTO updatedData) {
    return relationMPRepository.findById(id).map(existing -> {
        if (updatedData.startDate() != null) {
            existing.setStartDate(updatedData.startDate());
        }
        if (updatedData.endDate() != null) {
            existing.setEndDate(updatedData.endDate());
        }
        if (updatedData.dosage() != null) {
            existing.setDosage(updatedData.dosage());
        }
        if (updatedData.frequencyValue() != null) {
            existing.setFrequencyValue(updatedData.frequencyValue());
        }
        if (updatedData.frequencyUnit() != null) {
            existing.setFrequencyUnit(updatedData.frequencyUnit());
        }
        if (updatedData.patient() != null) {
            Patient patient = patientRepository.findById(updatedData.patient())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            existing.setPatient(patient);
        }
        if (updatedData.medication() != null) {
            Medication medication = medicationRepository.findById(updatedData.medication())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            existing.setMedication(medication);
        }
        if (updatedData.userId() != null) {
            User updater = userRepository.findById(updatedData.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));
            existing.setUpdatedBy(updater);
        }

        long totalUnits = switch (existing.getFrequencyUnit()) {
            case HOURS -> Duration.between(existing.getStartDate().atStartOfDay(), existing.getEndDate().plusDays(1).atStartOfDay()).toHours() / existing.getFrequencyValue();
            case DAYS -> ChronoUnit.DAYS.between(existing.getStartDate(), existing.getEndDate().plusDays(1)) / existing.getFrequencyValue();
            case WEEKS -> ChronoUnit.WEEKS.between(existing.getStartDate(), existing.getEndDate().plusDays(1)) / existing.getFrequencyValue();
        };

        int totalDosage = (int) totalUnits * existing.getDosage();
        existing.setTotalDosage(totalDosage);

        return relationMPRepository.save(existing);
    });
    }

    public boolean deleteRelationMP(Long id, Long userId) {
        return relationMPRepository.findById(id).map(relationMP -> {
            relationMP.setDeleted(!relationMP.isDeleted());
            User updatedBy = userRepository.findById(userId)
                                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            relationMP.setUpdatedBy(updatedBy);
            relationMPRepository.save(relationMP);
            return true;
        }).orElse(false);
    }
}
