package com.fatec.back.domain.User;

import lombok.Data;

@Data
public class UserRequestDTO {
    private String email;
    private String password;
    private String name;
    private Long roleId;
    private Long userId;
}
