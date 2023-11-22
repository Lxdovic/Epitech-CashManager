package com.moulamanager.api.repositories;

import com.moulamanager.api.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    Optional<UserModel> findById(long id);
    Optional<UserModel> findByUsername(String username);

    Boolean existsById(long id);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}