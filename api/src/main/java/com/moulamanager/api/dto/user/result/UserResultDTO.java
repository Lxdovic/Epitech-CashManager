package com.moulamanager.api.dto.user.result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moulamanager.api.models.UserModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResultDTO {
    private Long id;
    private String username;
    private String email;
    @JsonIgnore
    private String password;

    public static UserResultDTO fromUserModel(UserModel user) {
        return UserResultDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}
