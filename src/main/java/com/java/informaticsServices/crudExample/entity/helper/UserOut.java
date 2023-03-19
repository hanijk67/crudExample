package com.java.informaticsServices.crudExample.entity.helper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.java.informaticsServices.crudExample.entity.Gender;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserOut(String userName,
                      String userId,
                      String password,
                      Gender gender,
                      Boolean isDeleted) {
}
