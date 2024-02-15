package com.nhnacademy.jpa.service;

import com.nhnacademy.jpa.entity.Resident;
import com.nhnacademy.jpa.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Resident getUser(String id){
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    public Resident createUser(Resident resident){
        return userRepository.save(resident);
    }

    @Transactional
    public Resident modifyUser(Resident resident){
        return userRepository.save(resident);
    }
}
