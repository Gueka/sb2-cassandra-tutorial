package net.gueka.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.gueka.user.model.User;
import net.gueka.user.repository.UserRepository;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserRepository repository;

    @GetMapping(value = "/all")
    public List<User> getAllUsers(){
        return repository.findAll();
    }

    @PostMapping
    public User add(@RequestBody User user) {
        return repository.save(user);
    }

}