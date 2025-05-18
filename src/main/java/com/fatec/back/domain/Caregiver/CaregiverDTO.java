package com.fatec.back.domain.Caregiver;

/**
 * DTO (Data Transfer Object) utilizado para transferir dados de criação ou atualização
 * de um cuidador (Caregiver).
 * <p>
 * O {@code CaregiverDTO} é utilizado como objeto de entrada para as requisições de criação
 * ou atualização da entidade {@link Caregiver}. Ele carrega apenas os dados necessários
 * para essas operações, sem incluir dados adicionais da entidade.
 * </p>
 *
 * @param relation Tipo de relação com o paciente.
 * @param userId ID do usuário associado ao cuidador.
 * 
 * @see Caregiver
 */

public record CaregiverDTO(String relation, Long userId) {
}
