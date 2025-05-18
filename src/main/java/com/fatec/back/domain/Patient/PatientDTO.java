package com.fatec.back.domain.Patient;

import java.time.LocalDate;


/**
 * Data Transfer Object (DTO) para transferir dados relacionados ao paciente no sistema.
 * <p>
 * O {@code PatientDTO} é utilizado para a criação ou atualização de registros de pacientes,
 * permitindo que somente os dados necessários sejam enviados ou recebidos sem expor a entidade {@link Patient}.
 * </p>
 * 
 * @param birthday Data de nascimento do paciente.
 * @param patientCondition Condição médica do paciente.
 * @param userId Identificador do usuário associado ao paciente.
 */
public record PatientDTO (LocalDate birthday, String patientCondition, Long userId){
    
}
