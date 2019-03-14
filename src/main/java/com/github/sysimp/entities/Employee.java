package com.github.sysimp.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "employee")
@SequenceGenerator(name = "seq_employee", sequenceName = "seq_employee", allocationSize = 1)
public class Employee {
    @Id
    @Column(name = "id_employee")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_employee")
    private Integer id;

    @Column(name = "name_employee", unique = true)
    private String name;

    public Employee() {}

    public Employee(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id.equals(employee.id) &&
                name.equals(employee.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
