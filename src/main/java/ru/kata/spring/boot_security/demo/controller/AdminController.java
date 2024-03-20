package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/")
    public String getUsers(ModelMap model, Principal principal) {
        User user = userService.findByUserEmail(principal.getName());
        model.addAttribute("user", user);
        Set<User> list = userService.getUsers();
        model.addAttribute("list", list);
        model.addAttribute("roles", roleService.getRoles());
        return "users";
    }


    @PostMapping("/")
    public String addUser(@ModelAttribute("user") @Valid User user) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.setRoles(roleService.getRoles().stream().map(role -> roleService.findById(role.getId())).collect(Collectors.toSet()));
                userService.addUser(user);
        return "redirect:/admin/";
    }

    @GetMapping("/{id}/update")
    public String getEditUserForm(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("user", userService.getUser(id));
        return "userUpdate";
    }

    @PatchMapping("/{id}")
    public String saveUpdateUser(@ModelAttribute("user") @Valid User user, @PathVariable("id") Integer id) {
        userService.updateUser(user);
        return "redirect:/admin/";
    }

    @DeleteMapping("/{id}")
    public String removeUser(@PathVariable("id") Integer id) {
        userService.removeUser(id);
        return "redirect:/admin/";
    }
}