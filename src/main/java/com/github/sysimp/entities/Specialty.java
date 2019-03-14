package com.github.sysimp.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "specialty")
@SequenceGenerator(name = "seq_specialty", sequenceName = "seq_specialty", allocationSize = 1)
public class Specialty {
    @Id
    @Column(name = "id_specialty")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_specialty")
    private Integer id;

    @Column(name = "name_specialty", unique = true)
    private String name;

    public Specialty() {}

    public Specialty(Integer id, String name) {
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
        return "Specialty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Specialty specialty = (Specialty) o;
        return id.equals(specialty.id) &&
                name.equals(specialty.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
