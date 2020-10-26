package com.example.demo.data;

import com.example.demo.model.Gender;
import com.example.demo.model.User;
import lombok.Data;
import net.bytebuddy.utility.RandomString;

@Data
public class UserGenerator {
    public static User generateUser(Gender userGender, int userNameLength, int userSurnameLength) {
        if (userNameLength == -1 && userSurnameLength == -1)
            return new User(null, null, userGender);
        else if (userNameLength == -1)
            return new User(null, RandomString.make(userSurnameLength), userGender);
        else if (userSurnameLength == -1)
            return new User(RandomString.make(userNameLength), null, userGender);
        else
            return new User(RandomString.make(userNameLength), RandomString.make(userSurnameLength), userGender);
    }
}
