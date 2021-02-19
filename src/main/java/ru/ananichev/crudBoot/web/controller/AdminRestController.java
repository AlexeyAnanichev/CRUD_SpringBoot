package ru.ananichev.crudBoot.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.ananichev.crudBoot.model.Role;
import ru.ananichev.crudBoot.model.User;
import ru.ananichev.crudBoot.service.UserService;

import java.util.*;

@RestController
public class AdminRestController {

    private final UserService userService;

    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/showUsers")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

//    @GetMapping(value = "/admin/edit/{id}")
//    public String update(@ModelAttribute("user") User user, @RequestParam("editRoles") String[] roles) {
//        Set<Role> roleList = new HashSet<>();
//        for (String role : roles) {
//            roleList.add(userService.getRoleByName(role));
//        }
//        user.setRoles(roleList);
//        userService.updateUser(user);
//        return "redirect:/admin";
//    }

    @GetMapping(value = "/user/{id}")
    public User getUserById(@PathVariable("id") int id) {
        return userService.getUser(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") long id) {
        userService.removeUserById(id);
    }

    @PutMapping(value = "/rest/edit_user/{id}")
    public ResponseEntity<User> addNewUser(@PathVariable("id") long id, @RequestBody User user) {
        user.setId(id);
        userService.updateUser(user);
        return ResponseEntity.ok().body(user);
    }


}
