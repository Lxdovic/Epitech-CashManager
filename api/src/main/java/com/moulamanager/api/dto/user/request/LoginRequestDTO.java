package com.moulamanager.api.dto.user.request;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String username;
    private String password;
}
