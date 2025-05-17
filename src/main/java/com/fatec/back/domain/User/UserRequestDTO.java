package com.fatec.back.domain.User;

import com.fatec.back.domain.Role.Role;

public record UserRequestDTO(
    String email,
    String password,
    String name,
    Role role,
    Long userId)
{
}
