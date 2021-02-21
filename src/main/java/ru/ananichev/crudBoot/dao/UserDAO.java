package ru.ananichev.crudBoot.dao;


import ru.ananichev.crudBoot.model.Role;
import ru.ananichev.crudBoot.model.User;

import java.util.List;

public interface UserDAO {

    void saveUser(User user);

    void removeUserById(long id);

    List<User> getAllUsers();

    void updateUser(User user);

    User getUser(long id);

    User getUserByName(String username);

    Role getRoleByName(String name);

}
