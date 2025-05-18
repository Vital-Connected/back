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

/**
 * Serviço responsável pelas operações de negócio relacionadas ao histórico de medicação.
 * Esta classe contém métodos para manipulação de dados relacionados ao histórico de medicação,
 * incluindo a criação, atualização, exclusão e obtenção de registros de {@link History}.
 * 
 * O serviço interage diretamente com os repositórios de {@link History}, {@link RelationMP} e {@link User}
 * para realizar as operações de persistência.
 * 
 * <p>Métodos principais:</p>
 * <ul>
 *     <li>{@code getAllHistories()} - Recupera todos os históricos de medicação cadastrados no sistema.</li>
 *     <li>{@code getHistoryById(Long id)} - Recupera um histórico de medicação pelo ID.</li>
 *     <li>{@code createHistory(HistoryDTO dto)} - Cria um novo histórico de medicação com base nos dados fornecidos.</li>
 *     <li>{@code updateHistory(Long id, HistoryDTO updatedData)} - Atualiza os dados de um histórico de medicação existente.</li>
 *     <li>{@code deleteHistory(Long id, Long userId)} - Marca um histórico de medicação como deletado ou ativo novamente.</li>
 * </ul>
 * 
 * @see History
 * @see HistoryDTO
 * @see RelationMP
 * @see User
 * @see HistoryRepository
 * @see RelationMPRepository
 * @see UserRepository
 */
@Service
public class HistoryService {
    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private RelationMPRepository relationMPRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Recupera todos os históricos de medicação cadastrados no sistema.
     * 
     * @return Lista de todos os históricos.
     */
    public List<History> getAllHistories(){
        return historyRepository.findAll();
    }

    /**
     * Recupera um histórico de medicação pelo ID fornecido.
     * 
     * @param id ID do histórico de medicação a ser recuperado.
     * @return Um {@link Optional} contendo o histórico encontrado.
     */
    public Optional<History> getHistoryById(Long id){
        return historyRepository.findById(id);
    }

    /**
     * Cria um novo histórico de medicação com base nos dados fornecidos no DTO.
     * 
     * @param dto Dados de transferência (DTO) com as informações do histórico de medicação a ser criado.
     * @return O histórico recém-criado.
     */
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

    /**
     * Atualiza os dados de um histórico de medicação existente com base nos dados fornecidos no DTO.
     * 
     * @param id ID do histórico de medicação a ser atualizado.
     * @param updatedData Dados atualizados fornecidos no DTO.
     * @return O histórico atualizado, caso encontrado.
     */
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

     /**
     * Marca um histórico de medicação como deletado ou ativo novamente.
     * 
     * @param id ID do histórico de medicação a ser deletado ou reativado.
     * @param userId ID do usuário que está realizando a operação.
     * @return {@code true} se a operação foi bem-sucedida, {@code false} caso o histórico não seja encontrado.
     */
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
