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

/**
 * Serviço responsável pelas operações de negócios relacionadas ao vínculo entre cuidadores e pacientes.
 * Esta classe oferece métodos para a criação, atualização, exclusão e obtenção de registros de {@link Have},
 * que representam as relações entre um cuidador e um paciente em um período específico.
 * 
 * O serviço interage com os repositórios de {@link Have}, {@link User}, {@link Patient} e {@link Caregiver}
 * para realizar as operações de persistência.
 * 
 * <p>Métodos principais:</p>
 * <ul>
 *     <li>{@code getAllHaves()} - Recupera todos os vínculos de cuidadores e pacientes registrados.</li>
 *     <li>{@code getHaveById(Long id)} - Recupera um vínculo específico pelo ID.</li>
 *     <li>{@code createHave(HaveDTO dto)} - Cria um novo vínculo de cuidador-paciente com base nos dados fornecidos.</li>
 *     <li>{@code updateHave(Long id, HaveDTO updatedData)} - Atualiza os dados de um vínculo existente.</li>
 *     <li>{@code deleteHave(Long id, Long userId)} - Marca um vínculo como deletado ou ativo novamente.</li>
 * </ul>
 * 
 * @see Have
 * @see HaveDTO
 * @see Patient
 * @see Caregiver
 * @see User
 * @see HaveRepository
 * @see PatientRepository
 * @see CaregiverRepository
 * @see UserRepository
 */
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

    /**
     * Recupera todos os vínculos de cuidadores e pacientes registrados no sistema.
     * 
     * @return Lista de todos os vínculos.
     */
    public List<Have> getAllHaves(){
        return haveRepository.findAll();
    }

    /**
     * Recupera um vínculo específico de cuidador-paciente pelo ID fornecido.
     * 
     * @param id ID do vínculo a ser recuperado.
     * @return Um {@link Optional} contendo o vínculo encontrado.
     */
    public Optional<Have> getHaveById(Long id){
        return haveRepository.findById(id);
    }

    /**
     * Cria um novo vínculo de cuidador-paciente com base nos dados fornecidos no DTO.
     * 
     * @param dto Dados de transferência (DTO) com as informações do vínculo a ser criado.
     * @return O vínculo recém-criado.
     */
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

    /**
     * Atualiza os dados de um vínculo de cuidador-paciente existente com base nos dados fornecidos no DTO.
     * 
     * @param id ID do vínculo a ser atualizado.
     * @param updatedData Dados atualizados fornecidos no DTO.
     * @return O vínculo atualizado, caso encontrado.
     */
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

    /**
     * Marca um vínculo de cuidador-paciente como deletado ou ativo novamente.
     * 
     * @param id ID do vínculo a ser deletado ou reativado.
     * @param userId ID do usuário que está realizando a operação.
     * @return {@code true} se a operação foi bem-sucedida, {@code false} caso o vínculo não seja encontrado.
     */
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
