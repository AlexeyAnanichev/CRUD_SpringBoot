package ru.ananichev.crudBoot.web.controller;

import org.springframework.web.bind.annotation.*;
import ru.ananichev.crudBoot.model.User;
import ru.ananichev.crudBoot.service.UserService;

import java.util.*;

@RestController
public class AdminRestController {

    private final UserService userService;

    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/show")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(value = "/user/{id}")
    public User getUserById(@PathVariable("id") int id) {
        return userService.getUser(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") long id) {
        userService.removeUserById(id);
    }

    @PutMapping(value = "/edit/{id}")
    public void editUser(@PathVariable("id") long id, @RequestBody User user) {
        user.setId(id);
        userService.updateUser(user);
    }

    @PutMapping(value = "/save")
    public void saveUser(@RequestBody User user) {
        userService.saveUser(user);
    }


}
