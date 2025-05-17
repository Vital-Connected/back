package com.fatec.back.domain.User;

public record UserRequestDTO(
    String email,
    String password,
    String name,
    Long role,
    Long userId)
{
}
