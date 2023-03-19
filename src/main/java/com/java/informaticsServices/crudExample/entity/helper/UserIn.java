package com.java.informaticsServices.crudExample.entity.helper;

import com.java.informaticsServices.crudExample.entity.Gender;

import java.util.Objects;

public record UserIn (String userName,
                      String userId,
                      String password,
                      Gender gender) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserIn userIn = (UserIn) o;
        return userName.equals(userIn.userName) && userId.equals(userIn.userId) && gender.equals(userIn.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, userId, gender);
    }
}
