package com.example.demo.service;

import com.example.demo.data.UserGenerator;
import com.example.demo.model.Gender;
import com.example.demo.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplIntegrationTest {

    @Autowired
    UserService userService;


    @Test
    void addUserTest() {
        User user = UserGenerator.generateUser(Gender.getRandom(), 3, 4);
        User result = userService.addUser(user);
        List<User> userList = userService.getAllUser();
        assertNotNull(result.getId());
        assertEquals(1, userList.size());
        userService.deleteUser(user.getId()); // for prevent problems
    }

    @Test
    void getAllUserTest() {
        User user = UserGenerator.generateUser(Gender.getRandom(), 3, 4);

        List<User> previousUserList = userService.getAllUser();
        userService.addUser(user);
        List<User> afterUserList = userService.getAllUser();
        assertEquals(previousUserList.size() + 1, afterUserList.size());
        assertEquals(afterUserList.get(0), user);
        userService.deleteUser(user.getId()); // for prevent problems
    }

    @Test
    void getUserByIdTest() {
        User user = UserGenerator.generateUser(Gender.getRandom(), 3, 4);

        User result = userService.getUserById(user.getId());
        assertNull(result);

        userService.addUser(user);
        result = userService.getUserById(user.getId());
        assertEquals(user, result);
        userService.deleteUser(user.getId()); // for prevent problems
    }

    @Test
    void deleteUserTest() {
        User user = UserGenerator.generateUser(Gender.getRandom(), 3, 4);
        userService.addUser(user);
        userService.deleteUser(user.getId());
        assertEquals(0, userService.getAllUser().size());
    }

    @Test
    void updateUserTest() {
        User user = UserGenerator.generateUser(Gender.getRandom(), 3, 4);
        User updateUser = UserGenerator.generateUser(Gender.getRandom(), 6, 3);
        userService.addUser(user);

        User resultUser = userService.updateUser(user.getId(), updateUser);
        assertEquals(user.getId(), resultUser.getId());
        assertEquals(updateUser.getName(), resultUser.getName());
        userService.deleteUser(user.getId()); // for prevent problems
    }
}
