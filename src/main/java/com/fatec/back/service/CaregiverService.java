package com.fatec.back.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.back.domain.Caregiver.Caregiver;
import com.fatec.back.domain.Caregiver.CaregiverDTO;
import com.fatec.back.domain.User.User;
import com.fatec.back.repository.CaregiverRepository;
import com.fatec.back.repository.UserRepository;

@Service
public class CaregiverService {
    @Autowired
    private CaregiverRepository caregiverRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Caregiver> getAllCaregivers(){
        return caregiverRepository.findAll();
    }

    public Optional<Caregiver> getCaregiverById(Long id){
        return caregiverRepository.findById(id);
    }

    public Caregiver createCaregiver(CaregiverDTO dto) {
        User user = userRepository.findById(dto.userId())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        System.out.println(user);
        Caregiver caregiver = new Caregiver();
        caregiver.setRelation(dto.relation());
        caregiver.setCreated_by(user);
        caregiver.setUpdated_by(user);
        caregiver.setUser(user);

        return caregiverRepository.save(caregiver);
    }

   public Optional<Caregiver> updateCaregiver(Long id, CaregiverDTO updatedData) {
    return caregiverRepository.findById(id).map(existing -> {
        if (updatedData.relation() != null) {
            existing.setRelation(updatedData.relation());
        }
        if (updatedData.userId() != null) {
            User updater = userRepository.findById(updatedData.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));
            existing.setUpdated_by(updater);
        }

        return caregiverRepository.save(existing);
    });
    }

    public boolean deleteCaregiver(Long id, Long userId) {
        return caregiverRepository.findById(id).map(caregiver -> {
            caregiver.setDeleted(!caregiver.isDeleted());
            User updatedBy = userRepository.findById(userId)
                                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            caregiver.setUpdated_by(updatedBy);
            caregiverRepository.save(caregiver);
            return true;
        }).orElse(false);
    }
}
