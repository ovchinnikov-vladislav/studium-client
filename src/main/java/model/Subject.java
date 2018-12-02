package model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.Set;

public class Subject {
    private Integer idSubject;
    private String nameSubject;

    @JsonBackReference
    private Set<Group> groups;

    @JsonBackReference
    private Set<Test> tests;

    public Subject() {

    }

    public Integer getIdSubject() {
        return idSubject;
    }

    public void setIdSubject(Integer idSubject) {
        this.idSubject = idSubject;
    }

    public String getNameSubject() {
        return nameSubject;
    }

    public void setNameSubject(String nameSubject) {
        this.nameSubject = nameSubject;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public Set<Test> getTests() {
        return tests;
    }

    public void setTests(Set<Test> tests) {
        this.tests = tests;
    }
}
