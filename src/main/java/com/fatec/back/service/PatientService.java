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

/**
 * Serviço responsável pelas operações de negócio relacionadas aos pacientes. 
 * Esta classe contém métodos para manipulação de dados relacionados a pacientes, 
 * incluindo a criação, atualização, exclusão e obtenção de registros de {@link Patient}.
 * 
 * O serviço interage diretamente com os repositórios de {@link Patient} e {@link User} 
 * para realizar as operações de persistência.
 * 
 * <p>Métodos principais:</p>
 * <ul>
 *     <li>{@code getAllPatients()} - Recupera todos os pacientes cadastrados no sistema.</li>
 *     <li>{@code getPatientById(Long id)} - Recupera um paciente pelo ID.</li>
 *     <li>{@code createPatient(PatientDTO dto)} - Cria um novo paciente com base nos dados fornecidos.</li>
 *     <li>{@code updatePatient(Long id, PatientDTO updatedData)} - Atualiza os dados de um paciente existente.</li>
 *     <li>{@code deletePatient(Long id, Long userId)} - Marca um paciente como deletado ou ativo novamente.</li>
 * </ul>
 * 
 * @see Patient
 * @see PatientDTO
 * @see PatientRepository
 * @see UserRepository
 */
@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

     /**
     * Recupera todos os pacientes cadastrados no sistema.
     * 
     * @return Lista de todos os pacientes.
     */
    public List<Patient> getAllPatients(){
        return patientRepository.findAll();
    }

    /**
     * Recupera um paciente pelo ID fornecido.
     * 
     * @param id ID do paciente a ser recuperado.
     * @return Um {@link Optional} contendo o paciente encontrado.
     */
    public Optional<Patient> getPatientById(Long id){
        return patientRepository.findById(id);
    }

    /**
     * Cria um novo paciente com base nos dados fornecidos no DTO.
     * 
     * @param dto Dados de transferência (DTO) com as informações do paciente a ser criado.
     * @return O paciente recém-criado.
     */
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

    /**
     * Atualiza os dados de um paciente existente com base nos dados fornecidos no DTO.
     * 
     * @param id ID do paciente a ser atualizado.
     * @param updatedData Dados atualizados fornecidos no DTO.
     * @return A relação atualizada, caso encontrada.
     */
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

    /**
     * Marca um paciente como deletado ou ativo novamente.
     * 
     * @param id ID do paciente a ser deletado ou reativado.
     * @param userId ID do usuário que está realizando a operação.
     * @return {@code true} se a operação foi bem-sucedida, {@code false} caso o paciente não seja encontrado.
     */
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
