package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class RoleDaolmp implements RoleDao {
    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Set<Role> getRoles() {
        return entityManager.createQuery("from Role ", Role.class).getResultStream().collect(Collectors.toSet());
    }

    @Transactional
    public void addRole(Role role) {
        entityManager.persist(role);
    }

    @Override
    public Role findById(Integer id) {
        return entityManager.find(Role.class, id);
    }
}