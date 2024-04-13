package com.example.repositories;

import com.example.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

    List<Expense> findAllByGroup(Group group);

    @Override
    Expense save(Expense expense);

}
