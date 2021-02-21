package ru.ananichev.crudBoot.service;


import ru.ananichev.crudBoot.model.Role;
import ru.ananichev.crudBoot.model.User;

import java.util.List;

public interface UserService {

    void saveUser(User user);

    void removeUserById(long id);

    List<User> getAllUsers();

    void updateUser(User user);

    User getUser(long id);

    Role getRoleByName(String name);

    User getCurrentUser(String username);
}
