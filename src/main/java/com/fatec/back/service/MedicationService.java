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

/**
 * Serviço responsável pelas operações de negócio relacionadas à medicação. 
 * Esta classe contém métodos para manipulação de dados relacionados à medicação, 
 * incluindo a criação, atualização, exclusão e obtenção de registros de {@link Medication}.
 * 
 * O serviço interage diretamente com os repositórios de {@link Medication} e {@link User} 
 * para realizar as operações de persistência.
 * 
 * <p>Métodos principais:</p>
 * <ul>
 *     <li>{@code getAllMedications()} - Recupera todas as medicações cadastradas no sistema.</li>
 *     <li>{@code getMedicationById(Long id)} - Recupera uma medicação pelo ID.</li>
 *     <li>{@code createMedication(MedicationDTO dto)} - Cria uma nova medicação com base nos dados fornecidos.</li>
 *     <li>{@code updateMedication(Long id, MedicationDTO updatedData)} - Atualiza os dados de uma medicação existente.</li>
 *     <li>{@code deleteMedication(Long id, Long userId)} - Marca uma medicação como deletada ou ativa novamente.</li>
 * </ul>
 * 
 * @see Medication
 * @see MedicationDTO
 * @see MedicationRepository
 * @see UserRepository
 */
@Service
public class MedicationService {
    @Autowired
    private MedicationRepository medicationRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Recupera todas as medicações cadastradas no sistema.
     * 
     * @return Lista de todas as medicações.
     */
    public List<Medication> getAllMedications(){
        return medicationRepository.findAll();
    }

    /**
     * Recupera uma medicação pelo ID fornecido.
     * 
     * @param id ID da medicação a ser recuperada.
     * @return Um {@link Optional} contendo a medicação encontrada.
     */
    public Optional<Medication> getMedicationById(Long id){
        return medicationRepository.findById(id);
    }

     /**
     * Cria uma nova medicação com base nos dados fornecidos no DTO.
     * 
     * @param dto Dados de transferência (DTO) com as informações da medicação a ser criada.
     * @return A medicação recém-criada.
     */
    public Medication createMedication(MedicationDTO dto) {
        User user = userRepository.findById(dto.userId())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Medication medication = new Medication();
        medication.setName(dto.name());
        medication.setMedicationFunction(dto.medicationFunction());
        medication.setCreatedBy(user);
        medication.setUpdatedBy(user);

        return medicationRepository.save(medication);
    }

    /**
     * Atualiza os dados de uma medicação existente com base nos dados fornecidos no DTO.
     * 
     * @param id ID da medicação a ser atualizada.
     * @param updatedData Dados atualizados fornecidos no DTO.
     * @return A medicação atualizada, caso encontrada.
     */
    public Optional<Medication> updateMedication(Long id, MedicationDTO updatedData) {
    return medicationRepository.findById(id).map(existing -> {
        if (updatedData.name() != null) {
            existing.setName(updatedData.name());
        }
        if (updatedData.medicationFunction() != null) {
            existing.setMedicationFunction(updatedData.medicationFunction());
        }
        if (updatedData.userId() != null) {
            User updater = userRepository.findById(updatedData.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));
            existing.setUpdatedBy(updater);
        }

        return medicationRepository.save(existing);
    });
    }

    /**
     * Marca uma medicação como deletada ou ativa novamente.
     * 
     * @param id ID da medicação a ser deletada ou reativada.
     * @param userId ID do usuário que está realizando a operação.
     * @return {@code true} se a operação foi bem-sucedida, {@code false} caso a medicação não seja encontrada.
     */
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
