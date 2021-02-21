package ru.ananichev.crudBoot.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import ru.ananichev.crudBoot.model.User;
import ru.ananichev.crudBoot.service.UserService;

import java.security.Principal;
import java.util.*;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/admin";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/admin")
    public String getAllUsers(Model model, Principal principal) {
        model.addAttribute("userList", userService.getAllUsers());
        model.addAttribute("user", new User());
        principal.getName();
        model.addAttribute("currentUser", userService.getCurrentUser(principal.getName()));
        return "/admin/users";
    }

    @GetMapping("/user")
    public String getUser(Model model, Principal principal) {
        principal.getName();
        model.addAttribute("currentUser", userService.getCurrentUser(principal.getName()));
        return "user";
    }

    @RequestMapping(value = "hello", method = RequestMethod.GET)
    public String printWelcome(ModelMap model) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello!");
        messages.add("I'm Spring MVC-SECURITY application");
        model.addAttribute("messages", messages);
        return "hello";
    }
}


