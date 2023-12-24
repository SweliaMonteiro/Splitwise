package com.test.splitwise.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "Users")
public class User extends BaseModel {

    private String name;

    private String phoneNumber;

    private String password;

}
