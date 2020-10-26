package com.example.demo.service;

import com.example.demo.dal.UserDal;
import com.example.demo.data.UserGenerator;
import com.example.demo.model.Gender;
import com.example.demo.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplUnitTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserDal userDal;

    @Test
    void addUserTest() {
        User user = UserGenerator.generateUser(Gender.getRandom(), 3, 4);
        User mockUser = mock(User.class);

        when(mockUser.getId()).thenReturn(user.getId());
        when(userDal.save(any(User.class))).thenReturn(mockUser);
        User result = userService.addUser(user);

        assertNotEquals(user, result);
        assertEquals(user.getId(), result.getId());
    }


    @Test
    void getAllUserTest() {
        User user = UserGenerator.generateUser(Gender.getRandom(), 3, 4);

        when(userDal.findAll()).thenReturn(Collections.singletonList(user));
        List<User> userList = userService.getAllUser();
        assertEquals(1, userList.size());
        assertEquals(userList.get(0), user);
    }

    @Test
    void getUserByIdTest() {
        User user = UserGenerator.generateUser(Gender.getRandom(), 3, 4);
        User mockUser = mock(User.class);

        when(mockUser.getName()).thenReturn(user.getName());
        when(userDal.findById(any(UUID.class))).thenReturn(Optional.of(mockUser));
        User result = userService.getUserById(user.getId());

        assertNotEquals(user, result);
        assertEquals(user.getName(), result.getName());
    }

    // This test is not necessary
    @Test
    void deleteUserTest() {
        User user = UserGenerator.generateUser(Gender.getRandom(), 3, 4);
        userService.deleteUser(user.getId());
        verify(userDal, times(1)).deleteById(user.getId());
    }

    @Test
    void updateUserTest() {
        User user = UserGenerator.generateUser(Gender.getRandom(), 3, 4);
        User updateUser = UserGenerator.generateUser(Gender.getRandom(), 3, 4);
        updateUser.setId(user.getId());
        User mockUser = mock(User.class);

        when(mockUser.getId()).thenReturn(updateUser.getId());
        when(userDal.save(any(User.class))).thenReturn(mockUser);
        User result = userService.updateUser(user.getId(), updateUser);

        assertNotEquals(updateUser, result);
        assertEquals(updateUser.getId(), result.getId());
    }
}