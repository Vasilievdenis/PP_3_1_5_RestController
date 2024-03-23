package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImp;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class Init implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleServiceImp roleService;
    private final UserService userService;

    @Autowired
    public Init(RoleServiceImp roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Role userRole = new Role();
        userRole.setName("ROLE_USER");
        roleService.addRole(userRole);

        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");
        roleService.addRole(adminRole);

        Set<Role> userRoles = new HashSet<>(List.of(userRole));
        Set<Role> adminRoles = new HashSet<>(List.of(adminRole));

        User admin = new User();
        admin.setUsername("admin");
        admin.setLastname("Vasiliev");
        admin.setAge(30);
        admin.setEmail("admin@mail.ru");
        admin.setPassword("admin");
        admin.setRoles(adminRoles);
        userService.addUser(admin);

        User user = new User();
        user.setUsername("user");
        user.setLastname("user2");
        user.setAge(18);
        user.setEmail("user@mail.ru");
        user.setPassword("user");
        user.setRoles(userRoles);
        userService.addUser(user);
    }
}