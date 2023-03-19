package com.java.informaticsServices.crudExample.service;

import io.github.resilience4j.ratelimiter.RateLimiter;
import jakarta.annotation.PostConstruct;

public interface RateLimiterInitializer {

    void initialRateLimiter();

}
