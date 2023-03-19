package com.java.informaticsServices.crudExample.service;

import com.java.informaticsServices.crudExample.entity.User;
import com.java.informaticsServices.crudExample.entity.helper.UserConverter;
import com.java.informaticsServices.crudExample.entity.helper.UserIn;
import com.java.informaticsServices.crudExample.entity.helper.UserOut;
import com.java.informaticsServices.crudExample.repository.UserRepository;
import com.java.informaticsServices.crudExample.service.exception.UserExistException;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.vavr.CheckedRunnable;
import io.vavr.control.Try;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserService implements RateLimiterInitializer {
    Logger logger = LoggerFactory.getLogger(UserService.class);
    List<User> users = new ArrayList<>();
    UserRepository userRepository;
    private static RateLimiter rateLimiter;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    @Override
    public void initialRateLimiter() {
        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitRefreshPeriod(Duration.ofMillis(2000))
                .limitForPeriod(20)
                .timeoutDuration(Duration.ofMillis(10000))
                .build();
        RateLimiterRegistry registry = RateLimiterRegistry.of(config);
        rateLimiter = registry.rateLimiter("USERSERVICE", config);
    }

    public List<UserOut> findAll() {
        CheckedRunnable restrict = RateLimiter.decorateCheckedRunnable(rateLimiter, new CheckedRunnable() {
            @Override
            public void run() throws Throwable {
                users = userRepository.findAll();
            }
        });

        Try.run(restrict)
                .onFailure(throwable -> {
                    logger.error("Exception on UserService.findAll : ", throwable);
                    throw new RuntimeException(throwable);
                });

        return users.stream()
                .map(user -> UserConverter.convertToOut(user))
                .collect(Collectors.toList());

    }

    public UserOut findByUserId(String userId) {

        User user = userRepository.findByUserId(userId);
        return UserConverter.convertToOut(user);
    }

    public String save(UserIn userIn) {
        if (!checkUserExistForSave(userIn)) {
            User userConvert = UserConverter.convert(userIn);
            userConvert.setCreationDate(new Date(new java.util.Date().getTime()));
            userConvert.setUserStatus(true);
            User createdUser = userRepository.save(userConvert);
            logger.debug("UserService.save user : " + createdUser.getUserId() + " saved.");
            return createdUser.getUserId();
        } else {
            logger.warn("Exception on UserService.save user : " + userIn.userId() + " user exist");
            throw new UserExistException("Exception user exist!!!!");
        }
    }

    public String updateUser(UserIn userIn) {
        User findingUser = userRepository.findByUserId(userIn.userId());
        findingUser.setLastModifyDate(new Date(new java.util.Date().getTime()));
        if (userIn.userName() != null)
            findingUser.setUserName(userIn.userName());
        if (userIn.password() != null)
            findingUser.setPassword(userIn.password());
        if (userIn.gender() != null)
            findingUser.setGender(userIn.gender());

        User updatedUser = userRepository.save(findingUser);
        logger.debug("UserService.updateUser user : " + updatedUser.getUserId() + " updated.");

        return updatedUser.getUserId();
    }

    public void deleteUser(String userId) {
        User user = userRepository.findByUserId(userId);

        user.setUserStatus(false);
        userRepository.save(user);
        logger.debug("UserService.deleteUser user : " + user.getUserId() + " deleted.");
    }

    private Boolean checkUserExistForSave(UserIn userIn) {
        return userRepository.findByUserId(userIn.userId()) != null
                && userRepository.findByUserNameAndGender(userIn.userName(), userIn.gender()) != null;
    }


}