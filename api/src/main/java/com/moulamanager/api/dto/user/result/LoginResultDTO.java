package com.moulamanager.api.dto.user.result;

import com.moulamanager.api.services.UserDetailsImpl;
import lombok.Data;

@Data
public class LoginResultDTO {
    private String token;
    private UserResultDTO user;

    public LoginResultDTO(String token, UserDetailsImpl user) {
        this.token = token;
        this.user = new UserResultDTO();
        this.user.setId(user.getId());
        this.user.setUsername(user.getUsername());
        this.user.setEmail(user.getEmail());
    }
}