package com.example.demo.dal;

import com.example.demo.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao")
public class FakeDataAccessLayer {
    private static final List<User> DB = new ArrayList<>();

    public User save(User user) {
        DB.add(user);
        return user;
    }

    public List<User> findAll() {
        return DB;
    }

    public Optional<User> findById(UUID id) {
        return DB.stream().filter(user -> user.getId().equals(id)).findFirst();
    }

    public void deleteById(UUID id) {
        findById(id).ifPresent(DB::remove);
    }

    public User updateUser(UUID id, User update) {
        User previousUserInfo = this.findById(id).orElse(null);
        if (previousUserInfo != null) {
            int indexOfUser = DB.indexOf(previousUserInfo);
            User newUser = new User(update.getName(), update.getSurname(), update.getGender());
            newUser.setId(id);
            DB.set(indexOfUser, newUser);
            return newUser;
        }
        return null;
    }
}
