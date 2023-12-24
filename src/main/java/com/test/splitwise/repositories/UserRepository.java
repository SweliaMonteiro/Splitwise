package com.test.splitwise.repositories;

import com.test.splitwise.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByPhoneNumber(String phoneNumber);

    Optional<User> findByName(String userName);

    @Override
    User save(User user);

    @Override
    Optional<User> findById(Integer integer);

}
