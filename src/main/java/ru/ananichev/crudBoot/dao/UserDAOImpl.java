package ru.ananichev.crudBoot.dao;

import org.springframework.stereotype.Repository;
import ru.ananichev.crudBoot.model.Role;
import ru.ananichev.crudBoot.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO { //getRoleByName

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void removeUserById(long id) {
        entityManager.createQuery("DELETE FROM User WHERE id = :id").setParameter("id", id).executeUpdate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT user FROM User user").getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Role getRoleByName(String name) {
        return entityManager.createQuery("FROM Role WHERE name = :name", Role.class)
                .setParameter("name", name).getSingleResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> getAllRoles() {
        return entityManager.createQuery("FROM Role").getResultList();
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public User getUser(long id) {
        return entityManager.createQuery("SELECT user FROM User user WHERE user.id = :id", User.class) //сделать по подобию gerUserByName?
                .setParameter("id", id).getSingleResult();
    }

    @Override
    public User getUserByName(String username) {
        return entityManager.createQuery("from User where username = :username", User.class)
                .setParameter("username", username).getSingleResult();
    }
}
