package net.gueka.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.gueka.user.model.User;
import net.gueka.user.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository repository;


    public List<User> allUsers() {
        return repository.findAll();
    }

    public User addUser(User user) {
        return repository.save(user);
    }
}