package com.fatec.back.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.back.domain.Caregiver.Caregiver;
import com.fatec.back.domain.Have.Have;
import com.fatec.back.domain.Have.HaveDTO;
import com.fatec.back.domain.Patient.Patient;
import com.fatec.back.domain.User.User;
import com.fatec.back.repository.CaregiverRepository;
import com.fatec.back.repository.HaveRepository;
import com.fatec.back.repository.PatientRepository;
import com.fatec.back.repository.UserRepository;

@Service
public class HaveService {
    @Autowired
    private HaveRepository haveRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private CaregiverRepository caregiverRepository;

    public List<Have> getAllHaves(){
        return haveRepository.findAll();
    }

    public Optional<Have> getHaveById(Long id){
        return haveRepository.findById(id);
    }

    public Have createHave(HaveDTO dto) {
        User user = userRepository.findById(dto.userId())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Patient patient = patientRepository.findById(dto.patient())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Caregiver caregiver = caregiverRepository.findById(dto.caregiver())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Have have = new Have();
        have.setStartDate(dto.startDate());
        have.setEndDate(dto.endDate());
        have.setCaregiver(caregiver);
        have.setPatient(patient);
        have.setCreatedBy(user);
        have.setUpdatedBy(user);

        return haveRepository.save(have);
    }

   public Optional<Have> updateHave(Long id, HaveDTO updatedData) {
    return haveRepository.findById(id).map(existing -> {
        if (updatedData.startDate() != null) {
            existing.setStartDate(updatedData.startDate());
        }
        if (updatedData.endDate() != null) {
            existing.setEndDate(updatedData.endDate());
        }
        if (updatedData.patient() != null) {
            Patient patient = patientRepository.findById(updatedData.patient())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            existing.setPatient(patient);
        }
        if (updatedData.caregiver() != null) {
            Caregiver caregiver = caregiverRepository.findById(updatedData.caregiver())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            existing.setCaregiver(caregiver);
        }
        if (updatedData.userId() != null) {
            User updater = userRepository.findById(updatedData.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));
            existing.setUpdatedBy(updater);
        }

        return haveRepository.save(existing);
    });
    }

    public boolean deleteHave(Long id, Long userId) {
        return haveRepository.findById(id).map(have -> {
            have.setDeleted(!have.isDeleted());
            User updatedBy = userRepository.findById(userId)
                                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            have.setUpdatedBy(updatedBy);
            haveRepository.save(have);
            return true;
        }).orElse(false);
    }

}
