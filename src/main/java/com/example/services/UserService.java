package com.example.services;

import com.example.repositories.UserRepository;
import com.example.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User registerUser(String userName, String phoneNumber, String password) {
        User user = new User();

        // Set userName and password for thr User
        user.setName(userName);
        user.setPassword(password);

        // Set phone number for the User, make sure there is only one registration per mobile number
        Optional<User> userOptional = userRepository.findByPhoneNumber(phoneNumber);
        if(userOptional.isPresent()) {
            throw new RuntimeException("Mobile number already registered!");
        }
        user.setPhoneNumber(phoneNumber);

        // Save the User in the DB
        user = userRepository.save(user);
        return user;
    }

}
