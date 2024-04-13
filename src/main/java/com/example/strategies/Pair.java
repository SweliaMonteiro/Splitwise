package com.example.strategies;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pair {

    private int userId;

    private int amount;

    public Pair(int userId, int amount) {
        this.userId = userId;
        this.amount = amount;
    }

}
