package com.fatec.back.domain.Login;

/**
 * Data Transfer Object (DTO) utilizado para a resposta do login de um usuário no sistema.
 * <p>
 * O {@code LoginResponseDTO} encapsula o token gerado após a autenticação bem-sucedida do usuário.
 * Este token é um JWT (JSON Web Token) que serve como um identificador seguro para autenticar
 * e autorizar o usuário em requisições subsequentes ao sistema.
 * </p>
 * 
 * @param token Token JWT gerado para autenticação contínua do usuário.
 */
public record LoginResponseDTO(String token) {
}