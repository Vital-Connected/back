package com.fatec.back.domain.RelationMP;

import java.time.LocalDate;

import com.fatec.back.domain.RelationMP.RelationMP.FrequencyUnit;

/**
 * Data Transfer Object (DTO) para representar a relação entre um medicamento e um paciente.
 * <p>
 * O {@code RelationMPDTO} é utilizado para transferir os dados necessários para a criação ou atualização 
 * de uma relação entre um medicamento e um paciente, incluindo informações sobre dosagem, frequência de administração 
 * e o período de início e término do tratamento.
 * </p>
 * 
 * @param dosage Quantidade de medicamento a ser administrada por dose.
 * @param frequencyValue Número de vezes que o medicamento deve ser administrado por período.
 * @param frequencyUnit Unidade de tempo para a frequência (ex: "DIA", "HORA").
 * @param medication Identificador do medicamento associado à relação.
 * @param patient Identificador do paciente associado ao medicamento.
 * @param startDate Data de início do tratamento.
 * @param endDate Data de término do tratamento (pode ser nula).
 * @param userId Identificador do usuário responsável pela criação ou atualização dessa relação.
 */
public record RelationMPDTO (Integer dosage,Integer frequencyValue,FrequencyUnit frequencyUnit,Long medication,Long patient,LocalDate startDate,LocalDate endDate, Long userId){
    
}
