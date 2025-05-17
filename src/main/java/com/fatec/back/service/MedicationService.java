package com.fatec.back.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.back.domain.Medication.Medication;
import com.fatec.back.domain.Medication.MedicationDTO;
import com.fatec.back.domain.User.User;
import com.fatec.back.repository.MedicationRepository;
import com.fatec.back.repository.UserRepository;

@Service
public class MedicationService {
    @Autowired
    private MedicationRepository medicationRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Medication> getAllMedications(){
        return medicationRepository.findAll();
    }

    public Optional<Medication> getMedicationById(Long id){
        return medicationRepository.findById(id);
    }

    public Medication createMedication(MedicationDTO dto) {
        User user = userRepository.findById(dto.userId())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Medication medication = new Medication();
        medication.setName(dto.name());
        medication.setMedicationFunction(dto.medicationFunctnio());
        medication.setCreatedBy(user);
        medication.setUpdatedBy(user);

        return medicationRepository.save(medication);
    }

   public Optional<Medication> updateMedication(Long id, MedicationDTO updatedData) {
    return medicationRepository.findById(id).map(existing -> {
        if (updatedData.name() != null) {
            existing.setName(updatedData.name());
        }
        if (updatedData.medicationFunctnio() != null) {
            existing.setMedicationFunction(updatedData.medicationFunctnio());
        }
        if (updatedData.userId() != null) {
            User updater = userRepository.findById(updatedData.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));
            existing.setUpdatedBy(updater);
        }

        return medicationRepository.save(existing);
    });
    }

    public boolean deleteMedication(Long id, Long userId) {
        return medicationRepository.findById(id).map(medication -> {
            medication.setDeleted(!medication.isDeleted());
            User updatedBy = userRepository.findById(userId)
                                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            medication.setUpdatedBy(updatedBy);
            medicationRepository.save(medication);
            return true;
        }).orElse(false);
    }
}
