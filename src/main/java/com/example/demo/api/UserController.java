package com.example.demo.api;

import com.example.demo.dto.UserDto;
import com.example.demo.model.Gender;
import com.example.demo.model.User;
import com.example.demo.service.UserServiceImpl;
import com.example.demo.util.CustomErrorType;
import com.example.demo.validator.UuidCheckerConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@RequestMapping("api/v1/user")
@RestController
@Validated
public class UserController {
    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }


    // -------------------- Create a User ------------------------------------------------
    @PostMapping
    @ResponseBody
    public ResponseEntity<User> addUser(@RequestBody @Valid UserDto userDto) {

        User newUser = new User(userDto.getName(), userDto.getSurname(), Gender.valueOf(userDto.getGender()));
        return new ResponseEntity<>(userService.addUser(newUser), HttpStatus.CREATED);
    }


    // -------------------- Retrieve All Users -------------------------------------------
    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        List<User> users = userService.getAllUser();
        if (users.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // NO_CONTENT IS RETURN STATUS 204 and it is not a body or maybe use 404 ERROR
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // -------------------- Retrieve A  User with ID --------------------------------------
    @GetMapping(path = "{id}")
    public ResponseEntity<Object> getUserById(@PathVariable("id") @UuidCheckerConstraint @NotNull @Valid UUID id) {
        User user = userService.getUserById(id);
        if (user == null)
            return new ResponseEntity<>(new CustomErrorType("User with id " + id
                    + " is not found"), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // -------------------- Delete A User -------------------------------------------------
    @DeleteMapping(path = "del/", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Object> deleteUserById(@RequestBody @UuidCheckerConstraint @NotNull @Valid UUID id) {
        User user = userService.getUserById(id);
        if (user == null)
            return new ResponseEntity<>(new CustomErrorType("Unable to delete. User with id " + id + " not found"), HttpStatus.NOT_FOUND);

        userService.deleteUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // -------------------- Update A User -------------------------------------------------
    @PutMapping(path = "update/{id}", produces = ("application/json"))
    @ResponseBody
    public ResponseEntity<Object> updateUser(@PathVariable("id") @UuidCheckerConstraint UUID id, @RequestBody @Valid UserDto userDto) {
        User currentUser = userService.getUserById(id);
        if (currentUser == null)
            return new ResponseEntity<>(new CustomErrorType("Unable to update. User with id " + id + " not found"), HttpStatus.NOT_FOUND);
        User user = new User(userDto.getName(), userDto.getSurname(), Gender.valueOf(userDto.getGender()));
        User newUser = userService.updateUser(id, user);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }
}
