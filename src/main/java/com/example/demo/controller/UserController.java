package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


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


    @GetMapping("/users/view")
    public String showUsers(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> usersPage = userRepository.findAll(pageable);

        model.addAttribute("usersPage", usersPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalPages", usersPage.getTotalPages());

        return "user-list";
    }


    @PostMapping("/register")
    @ResponseBody
    public String submitForm(@RequestParam String name,
                             @RequestParam String surname,
                             @RequestParam Integer age,
                             @RequestParam String gender,
                             @RequestParam Double salary,
                             @RequestParam("imageFile") MultipartFile imageFile) {
        try {

            String uploadDir = "uploads/";
            String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
            Path filePath = Paths.get("src/main/resources/static/" + uploadDir + fileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, imageFile.getBytes());

            User user = new User(name, surname, age, gender, salary);
            user.setImagePath("/" + uploadDir + fileName);

            userRepository.save(user);

            return "{\"status\":\"success\"}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"status\":\"error\", \"message\":\"" + e.getMessage() + "\"}";
        }
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
