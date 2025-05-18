package com.fatec.back.domain.User;

/**
 * Data Transfer Object (DTO) usado para transferir dados de solicitação para criar ou atualizar um usuário.
 * <p>
 * Este objeto contém as informações necessárias para criar ou modificar um usuário no sistema. 
 * Ele inclui o e-mail, senha, nome do usuário, o ID do papel (role) do usuário e o ID do usuário que está realizando a ação.
 * </p>
 * 
 * @param email O e-mail do usuário para autenticação.
 * @param password A senha do usuário para autenticação.
 * @param name O nome completo do usuário.
 * @param role O ID do papel atribuído ao usuário.
 * @param userId O ID do usuário responsável pela criação ou atualização.
 */
public record UserRequestDTO(String email,String password,String name,Long role,Long userId){
}
