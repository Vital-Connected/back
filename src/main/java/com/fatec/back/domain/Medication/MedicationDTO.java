package com.fatec.back.domain.Medication;

/**
 * Data Transfer Object (DTO) que representa as informações necessárias para a criação ou atualização
 * de um medicamento no sistema.
 * 
 * Este DTO é usado para transferir dados entre as camadas do sistema, como controller e service,
 * sem expor a estrutura completa da entidade {@link Medication}.
 * 
 * @param name Nome do medicamento.
 * @param medicationFunction Função ou propósito do medicamento.
 * @param userId ID do usuário que está criando ou atualizando o medicamento.
 * 
 */
public record MedicationDTO (String name, String medicationFunction, Long userId) {
    
}
