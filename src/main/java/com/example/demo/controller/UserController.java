package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping("/register")
    public String showForm() {
        return "register";
    }

    @PostMapping("/register")
    @ResponseBody
    public String submitForm(@RequestParam String name,
                             @RequestParam String surname,
                             @RequestParam Integer age,
                             @RequestParam String gender,
                             @RequestParam Double salary) {
        User user = new User(name, surname, age, gender, salary);
        userRepository.save(user);
        return "{\"status\":\"success\"}";
    }



    @GetMapping("/users")
    @ResponseBody
    public List<User> getUsers() {
        return userRepository.findAll();
    }
    @DeleteMapping("/users/{id}")
    @ResponseBody
    public String deleteUser(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return "{\"status\":\"success\"}";
        } else {
            return "{\"status\":\"error\", \"message\":\"User not found\"}";
        }
    }

}
