package ru.kata.spring.boot_security.demo.dao;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class UserDaolmp implements UserDao {

    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {

        this.entityManager = entityManager;
    }

    @Override
    public Set<User> getUsers() {
        return entityManager.createQuery("from User", User.class).getResultStream().collect(Collectors.toSet());
    }

    @Transactional
    public User getUser(Integer id) {
        return entityManager.find(User.class, id);
    }

    @Override
    @Transactional
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    @Transactional
    public void removeUser(Integer id) {
        entityManager.remove(getUser(id));
    }

    public User findByUserEmail(String email) {
        String query = "select distinct u from User AS u left join fetch u.roles where u.email=:email";
        User user = entityManager.createQuery(query, User.class).setParameter("email", email).getSingleResult();
        if (user == null) {
            throw new UsernameNotFoundException("User" + email + "not found");
        }
        return user;
    }
}