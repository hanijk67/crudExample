package com.java.informaticsServices.crudExample.entity;

import java.io.Serializable;

public interface Entity<T> extends Serializable {
    T getId();
}
