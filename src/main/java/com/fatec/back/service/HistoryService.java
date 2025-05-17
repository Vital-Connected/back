package com.fatec.back.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.back.domain.History.History;
import com.fatec.back.domain.History.HistoryDTO;
import com.fatec.back.domain.RelationMP.RelationMP;
import com.fatec.back.domain.User.User;
import com.fatec.back.repository.HistoryRepository;
import com.fatec.back.repository.RelationMPRepository;
import com.fatec.back.repository.UserRepository;

@Service
public class HistoryService {
    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private RelationMPRepository relationMPRepository;

    @Autowired
    private UserRepository userRepository;

    public List<History> getAllHistories(){
        return historyRepository.findAll();
    }

    public Optional<History> getHistoryById(Long id){
        return historyRepository.findById(id);
    }

    public History createHistory(HistoryDTO dto) {
        User user = userRepository.findById(dto.userId())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        RelationMP relationMP = relationMPRepository.findById(dto.userId())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        History history = new History();
        history.setTaked(dto.taked());
        history.setUpdatedAt(dto.takedAt());
        history.setRelationMP(relationMP);
        history.setCreatedBy(user);
        history.setUpdatedBy(user);

        return historyRepository.save(history);
    }

   public Optional<History> updateHistory(Long id, HistoryDTO updatedData) {
    return historyRepository.findById(id).map(existing -> {
        if (updatedData.taked() != null) {
            existing.setTaked(updatedData.taked());
        }
        if (updatedData.takedAt() != null) {
            existing.setTakedAt(updatedData.takedAt());
        }
        if (updatedData.relationMP() != null) {
            RelationMP relationMP = relationMPRepository.findById(updatedData.relationMP())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            existing.setRelationMP(relationMP);
        }
        if (updatedData.userId() != null) {
            User updater = userRepository.findById(updatedData.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));
            existing.setUpdatedBy(updater);
        }

        return historyRepository.save(existing);
    });
    }

    public boolean deleteHistory(Long id, Long userId) {
        return historyRepository.findById(id).map(history -> {
            history.setDeleted(!history.isDeleted());
            User updatedBy = userRepository.findById(userId)
                                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            history.setUpdatedBy(updatedBy);
            historyRepository.save(history);
            return true;
        }).orElse(false);
    }
}
