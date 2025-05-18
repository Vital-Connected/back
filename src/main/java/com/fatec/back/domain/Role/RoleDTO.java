package com.fatec.back.domain.Role;

/**
 * Data Transfer Object (DTO) para representar o papel de um usuário no sistema.
 * <p>
 * O {@code RoleDTO} é utilizado para transferir dados sobre o papel de um usuário entre as diferentes camadas da aplicação, 
 * como da camada de controle para a camada de serviço ou de persistência, permitindo a criação ou atualização de registros 
 * relacionados ao papel.
 * </p>
 * 
 * @param name Nome do papel atribuído ao usuário.
 * @param description Descrição detalhada do papel e suas permissões.
 */
public record RoleDTO (String name, String description) {
    
}
