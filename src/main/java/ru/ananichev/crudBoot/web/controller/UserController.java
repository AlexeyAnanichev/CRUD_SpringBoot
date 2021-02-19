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
import java.util.*;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService,
                          @Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService){
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
//        return "/admin/test";

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
        model.addAttribute("currentUser", userService.getCurrentUser(principal.getName()));
        return "user";
    }

//    @DeleteMapping("/admin/{id}")
//    public String deleteUser(@PathVariable("id") long id) {
//        userService.removeUserById(id);
//        return "redirect:/admin";
//    }

//    @PostMapping(value = "/admin/edit/{id}")
//    public String update(@ModelAttribute("user") User user, @RequestParam("editRoles") String[] roles) {
//        Set<Role> roleList = new HashSet<>();;
//        for(String role : roles) {
//            roleList.add(userService.getRoleByName(role));
//        }
//        user.setRoles(roleList);
//        userService.updateUser(user);
//        return "redirect:/admin";
//    }

        @RequestMapping(value = "hello", method = RequestMethod.GET)
    public String printWelcome(ModelMap model) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello!");
        messages.add("I'm Spring MVC-SECURITY application");
        model.addAttribute("messages", messages);
        return "hello";
    }
}


