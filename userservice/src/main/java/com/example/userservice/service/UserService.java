package com.example.userservice.service;

import com.example.userservice.Entity.User;
import com.example.userservice.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public User create(User user) {
        return repo.save(user);
    }

    public Optional<User> findById(Long id) {
        return repo.findById(id);
    }

    public boolean exists(Long id) {
        return repo.existsById(id);
    }
}
