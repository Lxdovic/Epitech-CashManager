package com.moulamanager.api.dto.user.request;

import lombok.Data;

@Data
public class CreateUserRequestDTO {
    private String username;
    private String email;
    private String password;
}
