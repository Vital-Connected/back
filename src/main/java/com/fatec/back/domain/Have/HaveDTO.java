package com.fatec.back.domain.Have;

import java.time.LocalDate;

/**
 * DTO (Data Transfer Object) para representar um vínculo entre um paciente e um cuidador.
 * <p>
 * O {@code HaveDTO} é utilizado para transportar dados relacionados à criação ou atualização
 * de um vínculo entre um paciente e um cuidador. Ele contém as informações necessárias para
 * a criação ou atualização desse vínculo, como as datas de início e término, o paciente e o cuidador
 * associados, e o usuário responsável pela operação.
 * </p>
 * 
 * @param startDate Data de início do vínculo entre o paciente e o cuidador.
 * @param endDate Data de término do vínculo (pode ser nula).
 * @param patient ID do paciente associado ao vínculo.
 * @param caregiver ID do cuidador associado ao vínculo.
 * @param userId ID do usuário responsável pela criação ou atualização do vínculo.
 * 
 */
public record HaveDTO (LocalDate startDate, LocalDate endDate, Long patient, Long caregiver, Long userId){
    
}
