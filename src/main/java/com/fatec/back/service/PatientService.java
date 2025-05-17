package com.fatec.back.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.back.domain.Patient.Patient;
import com.fatec.back.domain.Patient.PatientDTO;
import com.fatec.back.domain.User.User;
import com.fatec.back.repository.PatientRepository;
import com.fatec.back.repository.UserRepository;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Patient> getAllPatients(){
        return patientRepository.findAll();
    }

    public Optional<Patient> getPatientById(Long id){
        return patientRepository.findById(id);
    }

    public Patient createPatient(PatientDTO dto) {
        User user = userRepository.findById(dto.userId())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        System.out.println(user);
        Patient patient = new Patient();
        patient.setBirthday(dto.birthday());
        patient.setPatientCondition(dto.patientCondition());
        patient.setCreated_by(user);
        patient.setUpdated_by(user);
        patient.setUser(user);

        return patientRepository.save(patient);
    }

   public Optional<Patient> updatePatient(Long id, PatientDTO updatedData) {
    return patientRepository.findById(id).map(existing -> {
        if (updatedData.birthday() != null) {
            existing.setBirthday(updatedData.birthday());
        }
        if (updatedData.patientCondition() != null) {
            existing.setPatientCondition(updatedData.patientCondition());
        }

        if (updatedData.userId() != null) {
            User updater = userRepository.findById(updatedData.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));
            existing.setUpdated_by(updater);
        }

        return patientRepository.save(existing);
    });
    }


    public boolean deletePatient(Long id, Long userId) {
        return patientRepository.findById(id).map(patient -> {
            patient.setDeleted(!patient.isDeleted());
            User updatedBy = userRepository.findById(userId)
                                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            patient.setUpdated_by(updatedBy);
            patientRepository.save(patient);
            return true;
        }).orElse(false);
    }
}
