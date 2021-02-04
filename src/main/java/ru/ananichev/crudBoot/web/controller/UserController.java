package ru.ananichev.crudBoot.web.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ananichev.crudBoot.model.Role;
import ru.ananichev.crudBoot.model.User;
import ru.ananichev.crudBoot.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class UserController {

    private final UserService userService;
    private final UserDetailsService userDetailsService;

    public UserController(UserService userService,
                          @Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/")
    public String index() {
        return "hello";
    }

    @GetMapping("/admin")
    public String getAllUsers(Model model) {
        model.addAttribute("userList", userService.getAllUsers());
        model.addAttribute("user", new User());
        return "/admin/users";
    }

    @PostMapping(value = "/admin/add")
    public String saveUser(@ModelAttribute @Valid User user, BindingResult bindingResult,
                           @RequestParam("roles") String[] roles) {
        if (bindingResult.hasErrors()) return "/admin/users";
        Set<Role> roleSet = new HashSet<>();
        for (String role : roles) {
            roleSet.add(userService.getRoleByName(role));
        }
        userService.saveUser(user, roleSet);
        return "redirect:/admin";
    }

    @GetMapping("/user")
    public String getUser(Model model, Principal principal) {
        principal.getName();
        model.addAttribute("userInfo", userDetailsService.loadUserByUsername(principal.getName()));
        return "user";
    }

    @DeleteMapping("/admin/{id}")
   // @ResponseBody
    public String deleteUser(@PathVariable("id") long id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/update/{id}")
    public String updatePage(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "admin/update";
    }

    @PostMapping(value = "/admin/edit")
    public String updateUser(@ModelAttribute @Valid User user, BindingResult bindingResult,
                             @RequestParam("roles") String[] roles) {
        if (bindingResult.hasErrors()) return "/admin/update/{id}";
        Set<Role> roleSet = new HashSet<>();
        for (String role : roles) {
            roleSet.add(userService.getRoleByName(role));
        }
        userService.updateUser(user, roleSet);
        return "redirect:/admin";
    }

    @RequestMapping(value = "hello", method = RequestMethod.GET)
    public String printWelcome(ModelMap model) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello!");
        messages.add("I'm Spring MVC-SECURITY application");
        model.addAttribute("messages", messages);
        return "hello";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}

