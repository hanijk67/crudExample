package com.java.informaticsServices.crudExample.repository;

import com.java.informaticsServices.crudExample.entity.Gender;
import com.java.informaticsServices.crudExample.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByUserStatus(Boolean isDelete);

    User findByUserId(String userId);


    User findByUserNameAndGender(String useraName, Gender gender);

}
