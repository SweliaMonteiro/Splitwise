package com.example.repositories;

import com.example.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface UserExpenseRepository extends JpaRepository<UserExpense, Integer> {

    List<UserExpense> findAllByUser(User user);

    @Override
    UserExpense save(UserExpense userExpense);

    List<UserExpense> findAllByExpense(Expense expense);

}
