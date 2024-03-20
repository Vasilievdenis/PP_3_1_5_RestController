package ru.kata.spring.boot_security.demo.model;


import lombok.Getter;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name_role")
    private String name;
    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Role() {
    }

    public Role(Integer id, Set<User> users) {
        this.id = id;
        this.users = users;
    }


    public Role(Integer id, String name, Set<User> users) {
        this.id = id;
        this.name = name;
        this.users = users;
    }

    public String setName(String name) {
        return this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        if (!id.equals(role.id)) return false;
        if (!name.equals(role.name)) return false;
        return users.equals(role.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return name;
    }
}