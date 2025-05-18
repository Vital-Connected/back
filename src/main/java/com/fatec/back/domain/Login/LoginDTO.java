package com.fatec.back.domain.Login;

/**
 * Data Transfer Object (DTO) utilizado para realizar o login de um usuário no sistema.
 * <p>
 * O {@code LoginDTO} encapsula as informações essenciais necessárias para a autenticação do usuário,
 * incluindo o e-mail e a senha. Esses dados são validados durante o processo de login para garantir
 * que o usuário seja identificado corretamente e autorizado a acessar o sistema.
 * </p>
 * 
 * @param email E-mail do usuário para autenticação.
 * @param password Senha do usuário para validação.
 * 
 */
public record LoginDTO(String email, String password) {
}
