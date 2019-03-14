package com.github.sysimp.entities;

public class SearchReportModel {
    private Employee employee;
    private Specialty specialty;

    private String localTimeStart;
    private String localTimeEnd;

    private String localDateStart;
    private String localDateEnd;

    private String description;

    public SearchReportModel() {}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getLocalTimeStart() {
        return localTimeStart;
    }

    public void setLocalTimeStart(String localTimeStart) {
        this.localTimeStart = localTimeStart;
    }

    public String getLocalTimeEnd() {
        return localTimeEnd;
    }

    public void setLocalTimeEnd(String localTimeEnd) {
        this.localTimeEnd = localTimeEnd;
    }

    public String getLocalDateStart() {
        return localDateStart;
    }

    public void setLocalDateStart(String localDateStart) {
        this.localDateStart = localDateStart;
    }

    public String getLocalDateEnd() {
        return localDateEnd;
    }

    public void setLocalDateEnd(String localDateEnd) {
        this.localDateEnd = localDateEnd;
    }

    @Override
    public String toString() {
        return "SearchReportModel{" +
                "description='" + description + '\'' +
                ", employee=" + employee +
                ", specialty=" + specialty +
                ", localTimeStart=" + localTimeStart +
                ", localTimeEnd=" + localTimeEnd +
                ", localDateStart=" + localDateStart +
                ", localDateEnd=" + localDateEnd +
                '}';
    }
}
