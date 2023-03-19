package com.java.informaticsServices.crudExample.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Gender implements Serializable {

    public static Gender MALE = new Gender("MALE");
    public static Gender FEMALE = new Gender("FEMALE");

    private String sex;

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Gender(String sex) {
        this.sex = sex;
    }

    public Gender() {
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static List<Gender> listAll(){
        return Arrays.asList(MALE, FEMALE);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gender gender = (Gender) o;
        return Objects.equals(sex, gender.sex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sex);
    }
}
