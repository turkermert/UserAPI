package com.example.demo.service;

import com.example.demo.dal.UserDal;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserDal userDal;

    @Autowired
    public UserServiceImpl(UserDal userDal) {
        this.userDal = userDal;
    }

    @Override
    public User addUser(User user) {
        return userDal.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userDal.findAll();
    }

    @Override
    public User getUserById(UUID id) {
        return userDal.findById(id).orElse(null);
    }


    @Override
    public void deleteUser(UUID id) {
        userDal.deleteById(id);
    }

    @Override
    public User updateUser(UUID id, User newUser) {
        newUser.setId(id);
        return userDal.save(newUser);
    }

}
