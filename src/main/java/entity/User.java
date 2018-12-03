package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.*;

public class User implements Serializable {

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
    private Group group;

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

    @Override
    public String toString() {
        return "idUser: " + idUser + "\n" +
                "fio: " + fio + "\n" +
                "login: " + login + "\n" +
                "group: " + group + "\n";
    }
}
