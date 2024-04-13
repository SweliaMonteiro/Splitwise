package com.example.models;

import com.example.enums.ExpenseType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;


@Getter
@Setter
@Entity
public class Expense extends BaseModel {

    private String description;

    private int amount;

    @ManyToOne
    private User createdBy;

    @Enumerated(EnumType.ORDINAL)
    private ExpenseType expenseType;

    @ManyToOne
    private Group group;

    @OneToMany(mappedBy = "expense")
    private List<UserExpense> userExpenses;

}
