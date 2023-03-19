package com.java.informaticsServices.crudExample;

import com.java.informaticsServices.crudExample.controller.UserController;
import com.java.informaticsServices.crudExample.entity.Gender;
import com.java.informaticsServices.crudExample.entity.User;
import com.java.informaticsServices.crudExample.entity.helper.UserConverter;
import com.java.informaticsServices.crudExample.entity.helper.UserIn;
import com.java.informaticsServices.crudExample.repository.UserRepository;
import com.java.informaticsServices.crudExample.service.UserService;
import com.java.informaticsServices.crudExample.service.exception.UserExistException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {


    private UserService userService;
    private UserRepository userRepository;
    private UserController userController;


    @Test
    void testSaveUser() {

        userController = mock(UserController.class);
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
        UserIn userIn = new UserIn("hani", "haniJK", "s123", Gender.MALE);
        User user = UserConverter.convert(userIn);
        when(userController.createUser(userIn)).then(invocationOnMock -> {
            userService.save(userIn);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        });

        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.findByUserId(userIn.userId())).thenReturn(null);
        when(userRepository.findByUserNameAndGender(userIn.userName(), userIn.gender())).thenReturn(null);

        userController.createUser(userIn);
        verify(userRepository, times(1)).save(user);

    }

    @Test
    void testUpdateUser(){
        userController = mock(UserController.class);
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);

        UserIn userIn = new UserIn("hani", "haniJK", "s123", Gender.MALE);
        User user = UserConverter.convert(userIn);

        when(userController.updateUser(userIn)).then(invocationOnMock -> {
            userService.updateUser(userIn);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        });
        when(userRepository.findByUserId(userIn.userId())).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        userController.updateUser(userIn);
        verify(userRepository, times(1)).save(user);
    }
}
