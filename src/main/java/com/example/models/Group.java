package com.example.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;


@Getter
@Setter
@Entity(name = "UserGroup")
public class Group extends BaseModel {

    private String name;

    @ManyToOne
    private User admin;

    @ManyToMany
    private List<User> members;

    @OneToMany(mappedBy = "group")
    private List<Expense> expenses;

}
