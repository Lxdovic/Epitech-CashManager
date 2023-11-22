package com.moulamanager.api.services.user;

import com.moulamanager.api.models.UserModel;

import java.util.List;

public interface IUserService {

    List<UserModel> findAll();

    UserModel findById(long id);

    UserModel save(UserModel user);

    UserModel update(UserModel user);

    void delete(long id);
}
