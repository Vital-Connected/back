package com.fatec.back.service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.back.domain.Medication.Medication;
import com.fatec.back.domain.Patient.Patient;
import com.fatec.back.domain.RelationMP.RelationMP;
import com.fatec.back.domain.RelationMP.RelationMPDTO;
import com.fatec.back.domain.User.User;
import com.fatec.back.repository.MedicationRepository;
import com.fatec.back.repository.PatientRepository;
import com.fatec.back.repository.RelationMPRepository;
import com.fatec.back.repository.UserRepository;

/**
 * Serviço responsável pelas operações de negócio relacionadas à relação entre 
 * medicamentos e pacientes. Esta classe contém métodos para manipulação de dados 
 * relacionados a essa relação, incluindo a criação, atualização, exclusão e 
 * obtenção de registros de {@link RelationMP}.
 * 
 * O serviço interage diretamente com os repositórios de {@link RelationMP}, 
 * {@link User}, {@link Patient} e {@link Medication} para realizar as operações 
 * de persistência.
 * 
 * <p>Métodos principais:</p>
 * <ul>
 *     <li>{@code getAllRelationMPs()} - Recupera todas as relações entre medicamentos e pacientes.</li>
 *     <li>{@code getRelationMPById(Long id)} - Recupera uma relação entre medicamento e paciente pelo ID.</li>
 *     <li>{@code createRelationMP(RelationMPDTO dto)} - Cria uma nova relação entre medicamento e paciente.</li>
 *     <li>{@code updateRelationMP(Long id, RelationMPDTO updatedData)} - Atualiza os dados de uma relação existente.</li>
 *     <li>{@code deleteRelationMP(Long id, Long userId)} - Marca uma relação como deletada ou ativa novamente.</li>
 * </ul>
 * 
 * @see RelationMP
 * @see RelationMPDTO
 * @see MedicationRepository
 * @see PatientRepository
 * @see UserRepository
 */
@Service
public class RelationMPService {
    @Autowired
    private RelationMPRepository relationMPRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MedicationRepository medicationRepository;

    /**
     * Recupera todas as relações entre medicamentos e pacientes cadastradas no sistema.
     * 
     * @return Lista de todas as relações entre medicamentos e pacientes.
     */
    public List<RelationMP> getAllRelationMPs(){
        return relationMPRepository.findAll();
    }

     /**
     * Recupera uma relação entre medicamento e paciente pelo seu ID.
     * 
     * @param id ID da relação a ser recuperada.
     * @return Um {@link Optional} contendo a relação encontrada.
     */
    public Optional<RelationMP> getRelationMPById(Long id){
        return relationMPRepository.findById(id);
    }

    /**
     * Cria uma nova relação entre medicamento e paciente com base nos dados fornecidos.
     * 
     * @param dto Dados de transferência (DTO) com as informações da relação a ser criada.
     * @return A relação recém-criada.
     */
    public RelationMP createRelationMP(RelationMPDTO dto) {
        User user = userRepository.findById(dto.userId())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Patient patient = patientRepository.findById(dto.patient())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Medication medication = medicationRepository.findById(dto.medication())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        RelationMP relationMP = new RelationMP();

        long totalUnits = switch (dto.frequencyUnit()) {
        case HOURS -> Duration.between(dto.startDate().atStartOfDay(), dto.endDate().plusDays(1).atStartOfDay()).toHours() / dto.frequencyValue();
        case DAYS -> ChronoUnit.DAYS.between(dto.startDate(), dto.endDate().plusDays(1)) / dto.frequencyValue();
        case WEEKS -> ChronoUnit.WEEKS.between(dto.startDate(), dto.endDate().plusDays(1)) / dto.frequencyValue();
        };

        int totalDosage = (int) totalUnits * dto.dosage();
        relationMP.setStartDate(dto.startDate());
        relationMP.setEndDate(dto.endDate());
        relationMP.setDosage(dto.dosage());
        relationMP.setFrequencyValue(dto.frequencyValue());
        relationMP.setFrequencyUnit(dto.frequencyUnit());
        relationMP.setMedication(medication);
        relationMP.setPatient(patient);
        relationMP.setCreatedBy(user);
        relationMP.setUpdatedBy(user);
        relationMP.setTotalDosage(totalDosage);

        return relationMPRepository.save(relationMP);
    }

    /**
     * Atualiza os dados de uma relação entre medicamento e paciente existente.
     * 
     * @param id ID da relação a ser atualizada.
     * @param updatedData Dados atualizados fornecidos no DTO.
     * @return A relação atualizada, caso encontrada.
     */
   public Optional<RelationMP> updateRelationMP(Long id, RelationMPDTO updatedData) {
    return relationMPRepository.findById(id).map(existing -> {
        if (updatedData.startDate() != null) {
            existing.setStartDate(updatedData.startDate());
        }
        if (updatedData.endDate() != null) {
            existing.setEndDate(updatedData.endDate());
        }
        if (updatedData.dosage() != null) {
            existing.setDosage(updatedData.dosage());
        }
        if (updatedData.frequencyValue() != null) {
            existing.setFrequencyValue(updatedData.frequencyValue());
        }
        if (updatedData.frequencyUnit() != null) {
            existing.setFrequencyUnit(updatedData.frequencyUnit());
        }
        if (updatedData.patient() != null) {
            Patient patient = patientRepository.findById(updatedData.patient())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            existing.setPatient(patient);
        }
        if (updatedData.medication() != null) {
            Medication medication = medicationRepository.findById(updatedData.medication())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            existing.setMedication(medication);
        }
        if (updatedData.userId() != null) {
            User updater = userRepository.findById(updatedData.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));
            existing.setUpdatedBy(updater);
        }

        long totalUnits = switch (existing.getFrequencyUnit()) {
            case HOURS -> Duration.between(existing.getStartDate().atStartOfDay(), existing.getEndDate().plusDays(1).atStartOfDay()).toHours() / existing.getFrequencyValue();
            case DAYS -> ChronoUnit.DAYS.between(existing.getStartDate(), existing.getEndDate().plusDays(1)) / existing.getFrequencyValue();
            case WEEKS -> ChronoUnit.WEEKS.between(existing.getStartDate(), existing.getEndDate().plusDays(1)) / existing.getFrequencyValue();
        };

        int totalDosage = (int) totalUnits * existing.getDosage();
        existing.setTotalDosage(totalDosage);

        return relationMPRepository.save(existing);
    });
    }

     /**
     * Marca uma relação entre medicamento e paciente como deletada ou ativa novamente.
     * 
     * @param id ID da relação a ser deletada ou reativada.
     * @param userId ID do usuário que está realizando a operação.
     * @return {@code true} se a operação foi bem-sucedida, {@code false} caso a relação não seja encontrada.
     */
    public boolean deleteRelationMP(Long id, Long userId) {
        return relationMPRepository.findById(id).map(relationMP -> {
            relationMP.setDeleted(!relationMP.isDeleted());
            User updatedBy = userRepository.findById(userId)
                                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            relationMP.setUpdatedBy(updatedBy);
            relationMPRepository.save(relationMP);
            return true;
        }).orElse(false);
    }
}
