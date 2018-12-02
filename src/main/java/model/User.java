package model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import javafx.beans.property.*;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Set;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {

    private Integer idUser;
    private String fio;
    private String login;
    private String password;
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a z")
    private Date dateReg;
    private String phone;
    private String email;
    private Byte statusPass;
    private String position;
    private Byte access;

    @JsonManagedReference
    private Group group;

    @JsonBackReference
    private Set<Answer> answers;

    @JsonBackReference
    private Set<Question> questions;

    @JsonBackReference
    private Set<Test> tests;

    @JsonBackReference
    private Set<ResultTest> resultTests;

    @JsonBackReference
    private Set<ResultQuestion> resultQuestions;

    public User() {
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDateReg() {
        return dateReg;
    }

    public void setDateReg(Date dateReg) {
        this.dateReg = dateReg;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Byte getStatusPass() {
        return statusPass;
    }

    public void setStatusPass(Byte statusPass) {
        this.statusPass = statusPass;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Byte getAccess() {
        return access;
    }

    public void setAccess(Byte access) {
        this.access = access;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Set<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    public Set<Test> getTests() {
        return tests;
    }

    public void setTests(Set<Test> tests) {
        this.tests = tests;
    }

    public Set<ResultTest> getResultTests() {
        return resultTests;
    }

    public void setResultTests(Set<ResultTest> resultTests) {
        this.resultTests = resultTests;
    }

    public Set<ResultQuestion> getResultQuestions() {
        return resultQuestions;
    }

    public void setResultQuestions(Set<ResultQuestion> resultQuestions) {
        this.resultQuestions = resultQuestions;
    }

    @Override
    public String toString() {
        return "ID: " + getIdUser() + "\n" +
                "FIO: " + getFio() + "\n" +
                "Login: " + getLogin() + "\n" +
                "DateReg: " + getDateReg()+ "\n" +
                "Phone: " + getPhone() + "\n" +
                "Email: " + getEmail() + "\n" +
                "Status: " + getStatusPass() + "\n" +
                "Position: " + getPosition() + "\n" +
                "Access: " + getAccess() + "\n";
    }
}
