package com.example.demo.dal;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserDal extends JpaRepository<User, UUID> {
}
