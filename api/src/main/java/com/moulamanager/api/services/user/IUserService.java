package com.moulamanager.api.services.user;

import com.moulamanager.api.dto.user.request.LoginRequestDTO;
import com.moulamanager.api.dto.user.result.LoginResultDTO;
import com.moulamanager.api.dto.user.result.UserResultDTO;
import com.moulamanager.api.models.UserModel;
import com.moulamanager.api.dto.user.request.CreateUserRequestDTO;

import java.util.List;

public interface IUserService {

    List<UserModel> findAll();

    UserModel findById(long id);

    UserResultDTO createUser(CreateUserRequestDTO signUpRequest);

    LoginResultDTO authenticateUser(LoginRequestDTO loginRequest);

    UserModel update(UserModel user);

    void delete(long id);
}
