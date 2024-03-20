package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Set;

@Service
@Transactional(readOnly = true)
public class RoleServiceImp implements RoleService {
    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImp(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Set<Role> getRoles() {
        return roleDao.getRoles();
    }

    @Override
    public Role findById(Integer id) {
        return roleDao.findById(id);
    }

    @Override
    @Transactional
    public void addRole(Role role) {
        roleDao.addRole(role);
    }
}