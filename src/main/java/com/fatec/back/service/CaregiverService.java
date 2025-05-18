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

/**
 * Serviço responsável pelas operações de negócios relacionadas aos cuidadores.
 * Esta classe oferece métodos para a criação, atualização, exclusão e obtenção de registros de {@link Caregiver},
 * que representam os cuidadores associados aos pacientes.
 * 
 * O serviço interage com os repositórios de {@link Caregiver} e {@link User} para realizar as operações de persistência.
 * 
 * <p>Métodos principais:</p>
 * <ul>
 *     <li>{@code getAllCaregivers()} - Recupera todos os cuidadores registrados.</li>
 *     <li>{@code getCaregiverById(Long id)} - Recupera um cuidador específico pelo ID.</li>
 *     <li>{@code createCaregiver(CaregiverDTO dto)} - Cria um novo cuidador com base nos dados fornecidos.</li>
 *     <li>{@code updateCaregiver(Long id, CaregiverDTO updatedData)} - Atualiza os dados de um cuidador existente.</li>
 *     <li>{@code deleteCaregiver(Long id, Long userId)} - Marca um cuidador como deletado ou ativo novamente.</li>
 * </ul>
 * 
 * @see Caregiver
 * @see CaregiverDTO
 * @see User
 * @see CaregiverRepository
 * @see UserRepository
 */
@Service
public class CaregiverService {
    @Autowired
    private CaregiverRepository caregiverRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Recupera todos os cuidadores registrados no sistema.
     * 
     * @return Lista de todos os cuidadores.
     */
    public List<Caregiver> getAllCaregivers(){
        return caregiverRepository.findAll();
    }

    /**
     * Recupera um cuidador específico pelo ID fornecido.
     * 
     * @param id ID do cuidador a ser recuperado.
     * @return Um {@link Optional} contendo o cuidador encontrado.
     */
    public Optional<Caregiver> getCaregiverById(Long id){
        return caregiverRepository.findById(id);
    }
    
    /**
     * Cria um novo cuidador com base nos dados fornecidos no DTO.
     * 
     * @param dto Dados de transferência (DTO) com as informações do cuidador a ser criado.
     * @return O cuidador recém-criado.
     */
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

     /**
     * Atualiza os dados de um cuidador existente com base nos dados fornecidos no DTO.
     * 
     * @param id ID do cuidador a ser atualizado.
     * @param updatedData Dados atualizados fornecidos no DTO.
     * @return O cuidador atualizado, caso encontrado.
     */
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

    /**
     * Marca um cuidador como deletado ou ativo novamente.
     * 
     * @param id ID do cuidador a ser deletado ou reativado.
     * @param userId ID do usuário que está realizando a operação.
     * @return {@code true} se a operação foi bem-sucedida, {@code false} caso o cuidador não seja encontrado.
     */
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
