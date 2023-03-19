package com.java.informaticsServices.crudExample.controller;


import com.java.informaticsServices.crudExample.entity.helper.UserIn;
import com.java.informaticsServices.crudExample.entity.helper.UserOut;
import com.java.informaticsServices.crudExample.service.UserService;
import com.java.informaticsServices.crudExample.service.exception.UserExistException;
import io.github.resilience4j.ratelimiter.RateLimiter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.BeanDefinitionDsl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    UserService userService;

    @GetMapping("")
    public ResponseEntity<List<UserOut>> getAllUsers(HttpServletRequest request, HttpServletResponse response) {
        try {
            return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Exception on UserController.getAllUsers : ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserOut> getByUserId(@PathVariable("userId") String userId, HttpServletRequest request, HttpServletResponse response) {
        try {
            UserOut user = userService.findByUserId(userId);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Exception on UserController.getByUserId : ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    @Secured("ROLE_USER")
    public ResponseEntity<String> createUser(@RequestBody UserIn userIn) {
        try {
            String userId = userService.save(userIn);
            logger.debug("UserController.createUser user : " + userId + " created");

            return new ResponseEntity<>(userId, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Exception on UserController.createUser : ", e);
            if (e instanceof UserExistException)
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody UserIn user) {
        try {

            UserOut findingUser = userService.findByUserId(user.userId());

            if (findingUser != null) {
                String userId = userService.updateUser(user);
                logger.debug("UserController.updateUser user : " + userId + " updates");
                return new ResponseEntity<>(userId, HttpStatus.OK);
            }
            logger.debug("UserController.updateUser user : " + user.userId() + " notFound");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Exception on UserController.updateUser : ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<HttpStatus> deleteByUserId(@PathVariable("userId") String userId, HttpServletRequest request, HttpServletResponse response) {
        try {
            String admin = request.getHeader("admin");
            if (admin != null) {
                UserOut findingUser = userService.findByUserId(userId);
                if (findingUser != null) {
                    userService.deleteUser(userId);
                    logger.debug("UserController.deleteByUserId user : " + userId + " updates");
                    return new ResponseEntity<>(HttpStatus.OK);
                }
                logger.debug("UserController.deleteByUserId user : " + userId + " notFound");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            logger.error("Exception on UserController.deleteByUserId : ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
