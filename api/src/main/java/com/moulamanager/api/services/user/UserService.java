package com.moulamanager.api.services.user;

import com.moulamanager.api.exceptions.user.UserNotFoundException;
import com.moulamanager.api.models.UserModel;
import com.moulamanager.api.repositories.UserRepository;
import com.moulamanager.api.services.AbstractService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService extends AbstractService<UserModel> implements IUserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserModel> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserModel findById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public UserModel save(UserModel user) {
        return userRepository.save(user);
    }

    @Override
    public UserModel update(UserModel user) {
        return null;
    }

    @Override
    public void delete(long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }
}
