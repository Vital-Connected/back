package com.fatec.back.domain.History;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) para transferir informações relacionadas ao histórico de administração de medicamentos
 * ou outras ações realizadas com o paciente.
 * <p>
 * O {@code HistoryDTO} encapsula os dados essenciais sobre a administração de um medicamento ou qualquer outro evento
 * relevante, como a indicação de que o medicamento foi administrado e o momento em que essa administração ou ação ocorreu.
 * Esse DTO é utilizado para armazenar o status da administração do medicamento, incluindo o momento exato da ação e
 * o usuário responsável por realizá-la.
 * </p>
 * 
 * @param taked Indica se o medicamento foi administrado (true/false).
 * @param takedAt Data e hora de quando o medicamento foi administrado ou a ação ocorreu.
 * @param relationMP Identificador da relação entre o medicamento e o paciente.
 * @param userId Identificador do usuário responsável pela ação.
 * 
 */
public record HistoryDTO (Boolean taked, LocalDateTime takedAt, Long relationMP,Long userId){
    
}
