package com.moulamanager.api.services.user;

import com.moulamanager.api.dto.user.request.CreateUserRequestDTO;
import com.moulamanager.api.dto.user.request.LoginRequestDTO;
import com.moulamanager.api.dto.user.result.LoginResultDTO;
import com.moulamanager.api.exceptions.user.UserAlreadyExistsException;
import com.moulamanager.api.exceptions.user.UserNotFoundException;
import com.moulamanager.api.models.UserModel;
import com.moulamanager.api.repositories.UserRepository;
import com.moulamanager.api.services.AbstractService;
import com.moulamanager.api.services.jwt.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService extends AbstractService<UserModel> implements IUserService {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private static final String USER_WITH_EMAIL_EXISTS = "User with email %s already exists";
    private static final String USER_WITH_USERNAME_EXISTS = "User with username %s already exists";
    private static final String USER_NOT_FOUND = "User not found";

    @Override
    public List<UserModel> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserModel findById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public LoginResultDTO registerUser(CreateUserRequestDTO signUpRequest) {
        checkIfUserWithSameEmailOrUsernameExists(signUpRequest.getEmail(), signUpRequest.getUsername());

        createNewUser(signUpRequest);

        return authenticateUserWithToken(signUpRequest.getUsername(), signUpRequest.getPassword());
    }

    public LoginResultDTO authenticateUser(LoginRequestDTO loginRequest) {
        return authenticateUserWithToken(loginRequest.getUsername(), loginRequest.getPassword());
    }

    @Override
    public UserModel update(UserModel user) {
        return null;
    }

    @Override
    public void delete(long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(USER_NOT_FOUND);
        }
        userRepository.deleteById(id);
    }

    private void createNewUser(CreateUserRequestDTO signUpRequest) {
        UserModel user = UserModel.builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(encoder.encode(signUpRequest.getPassword()))
                .build();
        userRepository.save(user);
    }

    private LoginResultDTO authenticateUserWithToken(String username, String password) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return new LoginResultDTO(jwt, userDetails);
    }

    private void checkIfUserWithSameEmailOrUsernameExists(String email, String username) {
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException(String.format(USER_WITH_EMAIL_EXISTS, email));
        }
        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistsException(String.format(USER_WITH_USERNAME_EXISTS, username));
        }
    }

}
