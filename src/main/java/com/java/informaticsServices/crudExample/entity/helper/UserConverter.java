package com.java.informaticsServices.crudExample.entity.helper;

import com.java.informaticsServices.crudExample.entity.User;

public class UserConverter {

    public static User convert(UserIn userIn) {
        User user = new User();
        user.setUserName(userIn.userName());
        user.setGender(userIn.gender());
        user.setPassword(userIn.password());
        user.setUserId(userIn.userId());
        return user;
    }


    public static UserIn convertToIn(User user) {
        return new UserIn(user.getUserName(), user.getUserId(), user.getPassword(),
                user.getGender());
    }


    public static UserOut convertToOut(User user) {
        return new UserOut(user.getUserName(), user.getUserId(), user.getPassword(),
                user.getGender(), !user.getUserStatus());
    }

}
