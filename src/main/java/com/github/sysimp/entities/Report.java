package com.github.sysimp.entities;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "report")
@SequenceGenerator(name = "seq_report", sequenceName = "seq_report", allocationSize = 1)
public class Report {
    @Id
    @Column(name = "id_report")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_report")
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_employee")
    private Employee employee;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_specialty")
    private Specialty specialty;

    @Column(name = "date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate localDate;

    @Column(name = "time_start")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime localTimeStart;

    @Column(name = "time_end")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime localTimeEnd;

    private String description;

    @Transient
    private String strDate;
    @Transient
    private String strTimeStart;
    @Transient
    private String strTimeEnd;

    public Report() {}

    public Report(Employee employee, Specialty specialty, LocalDate localDate,
                  LocalTime localTimeStart, LocalTime localTimeEnd, String description) {
        this.employee = employee;
        this.specialty = specialty;
        this.localDate = localDate;
        this.localTimeStart = localTimeStart;
        this.localTimeEnd = localTimeEnd;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public LocalTime getLocalTimeStart() {
        return localTimeStart;
    }

    public void setLocalTimeStart(LocalTime localTimeStart) {
        this.localTimeStart = localTimeStart;
    }

    public LocalTime getLocalTimeEnd() {
        return localTimeEnd;
    }

    public void setLocalTimeEnd(LocalTime localTimeEnd) {
        this.localTimeEnd = localTimeEnd;
    }

    public String getDescription() {
        return description;
    }

    public String getStrDate() {
        return strDate;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }

    public String getStrTimeStart() {
        return strTimeStart;
    }

    public void setStrTimeStart(String strTimeStart) {
        this.strTimeStart = strTimeStart;
    }

    public String getStrTimeEnd() {
        return strTimeEnd;
    }

    public void setStrTimeEnd(String strTimeEnd) {
        this.strTimeEnd = strTimeEnd;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", employee=" + employee +
                ", specialty=" + specialty +
                ", localDate=" + localDate +
                ", localTimeStart=" + localTimeStart +
                ", localTimeEnd=" + localTimeEnd +
                ", description='" + description + '\'' +
                ", strDate='" + strDate + '\'' +
                ", strTimeStart='" + strTimeStart + '\'' +
                ", strTimeEnd='" + strTimeEnd + '\'' +
                '}';
    }
}

