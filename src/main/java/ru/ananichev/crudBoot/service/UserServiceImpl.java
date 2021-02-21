package ru.ananichev.crudBoot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ananichev.crudBoot.dao.UserDAO;
import ru.ananichev.crudBoot.model.Role;
import ru.ananichev.crudBoot.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service("userDetailsServiceImpl")
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Transactional
    @Override
    public void saveUser(User user) {
        Set<Role> roles = new HashSet<>();
        user.getRoles().forEach(role -> roles.add(getRoleByName(role.toString())));
        user.setRoles(roles);
        userDAO.saveUser(user);
    }

    @Transactional
    @Override
    public void removeUserById(long id) {
        userDAO.removeUserById(id);
    }

    @Transactional
    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Transactional
    @Override
    public void updateUser(User user) {
        Set<Role> roles = new HashSet<>();
        user.getRoles().forEach(role -> roles.add(getRoleByName(role.toString())));
        user.setRoles(roles);
        userDAO.updateUser(user);
    }

    @Transactional
    @Override
    public User getUser(long id) {
        return userDAO.getUser(id);
    }

    @Override
    @Transactional
    public Role getRoleByName(String name) {
        return userDAO.getRoleByName(name);
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.getUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User $s not found", username));
        }
        return user;
    }

    @Transactional
    @Override
    public User getCurrentUser(String username) {
        User user = userDAO.getUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User $s not found", username));
        }
        return user;
    }
}
