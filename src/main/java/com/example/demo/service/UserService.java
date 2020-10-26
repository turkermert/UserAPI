package com.example.demo.service;

import com.example.demo.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User addUser(User user);

    List<User> getAllUser();

    User getUserById(UUID id);

    void deleteUser(UUID id);

    User updateUser(UUID id, User newUser);
}
