package com.test.splitwise.repositories;

import com.test.splitwise.models.Expense;
import com.test.splitwise.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

    List<Expense> findAllByGroup(Group group);

    @Override
    Expense save(Expense expense);

}
