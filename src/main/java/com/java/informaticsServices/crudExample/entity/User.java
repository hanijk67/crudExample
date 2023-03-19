package com.java.informaticsServices.crudExample.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import java.util.Objects;

@Entity
@Table(name = "TBL_USER")
@GenericGenerator(name = "sequence_generator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
                @org.hibernate.annotations.Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SEQ_USER"),
                @org.hibernate.annotations.Parameter(name = SequenceStyleGenerator.OPT_PARAM, value = "pooled"),
                @org.hibernate.annotations.Parameter(name = SequenceStyleGenerator.INITIAL_PARAM, value = "1000"),
                @org.hibernate.annotations.Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1")
        })
public class User extends BaseEntity<Long> {

    @Id
    @GeneratedValue(generator = "sequence_generator")
    @Column(name = "ID")
    Long id;

    @Column(name = "USRNAME", unique = true)
    String userName;

    @Column(name = "USRID", unique = true)
    String userId;

    @Column(name = "PASSWD", unique = true)
    String password;

    @Column(name = "USRSTATUS")
    Boolean userStatus;

    @AttributeOverrides({
            @AttributeOverride(name = "type", column = @Column(name = "GENDER"))
    })
    Gender gender;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Boolean userStatus) {
        this.userStatus = userStatus;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userName, user.userName) && Objects.equals(userId, user.userId) && Objects.equals(gender, user.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, userId, gender);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", userStatus=" + userStatus +
                ", gender=" + gender +
                '}';
    }
}
