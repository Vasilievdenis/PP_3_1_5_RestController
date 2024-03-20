package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.dao.UserDaolmp;
import ru.kata.spring.boot_security.demo.model.User;

import java.security.Principal;

@Controller
public class UserController {

    private final UserDaolmp userDaolmp;

    @Autowired
    public UserController(UserDaolmp userDaolmp) {
        this.userDaolmp = userDaolmp;
    }

    @GetMapping("/user")
    public String followUser(Model model, Principal principal) {
        User user = userDaolmp.findByUserEmail(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }
}