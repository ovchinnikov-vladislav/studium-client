package ru.kamchatgtu.studium.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class User implements Serializable {

    private IntegerProperty idUser;
    private StringProperty fio;
    private StringProperty login;
    private StringProperty password;
    private ObjectProperty<Date> dateReg;
    private ObjectProperty<Date> dateAuth;
    private StringProperty phone;
    private StringProperty email;
    private IntegerProperty status;
    private ObjectProperty<Role> role;
    private ObjectProperty<Group> group;
    private ObjectProperty<Direction> direction;

    public User() {
        idUser = new SimpleIntegerProperty();
        fio = new SimpleStringProperty();
        login = new SimpleStringProperty();
        password = new SimpleStringProperty();
        dateReg = new SimpleObjectProperty<>();
        dateAuth = new SimpleObjectProperty<>();
        phone = new SimpleStringProperty();
        email = new SimpleStringProperty();
        status = new SimpleIntegerProperty();
        role = new SimpleObjectProperty<>();
        group = new SimpleObjectProperty<>();
        direction = new SimpleObjectProperty<>();
    }

    @JsonIgnore
    public void setUser(User user) {
        setIdUser(user.getIdUser());
        setLogin(user.getLogin());
        setFio(user.getFio());
        setDateReg(user.getDateReg());
        setDateAuth(user.getDateAuth());
        setEmail(user.getEmail());
        setPhone(user.getPhone());
        setRole(user.getRole());
        setGroup(user.getGroup());
        setDirection(user.getDirection());
        setPassword(user.getPassword());
        setStatus(user.getStatus());
    }

    public int getIdUser() {
        return idUser.get();
    }

    public IntegerProperty idUserProperty() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser.set(idUser);
    }

    public String getFio() {
        return fio.get();
    }

    public StringProperty fioProperty() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio.set(fio);
    }

    public String getLogin() {
        return login.get();
    }

    public StringProperty loginProperty() {
        return login;
    }

    public void setLogin(String login) {
        this.login.set(login);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public Date getDateReg() {
        return dateReg.get();
    }

    public ObjectProperty<Date> dateRegProperty() {
        return dateReg;
    }

    public void setDateReg(Date dateReg) {
        this.dateReg.set(dateReg);
    }

    public Date getDateAuth() {
        return dateAuth.get();
    }

    public ObjectProperty<Date> dateAuthProperty() {
        return dateAuth;
    }

    public void setDateAuth(Date dateAuth) {
        this.dateAuth.set(dateAuth);
    }

    public String getPhone() {
        return phone.get();
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public int getStatus() {
        return status.get();
    }

    public void setStatus(int status) {
        this.status.set(status);
    }

    public IntegerProperty statusProperty() {
        return status;
    }

    public Role getRole() {
        return role.get();
    }

    public ObjectProperty<Role> roleProperty() {
        return role;
    }

    public void setRole(Role role) {
        this.role.set(role);
    }

    public Group getGroup() {
        return group.get();
    }

    public ObjectProperty<Group> groupProperty() {
        return group;
    }

    public void setGroup(Group group) {
        this.group.set(group);
    }

    public Direction getDirection() {
        return direction.get();
    }

    public ObjectProperty<Direction> directionProperty() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction.set(direction);
    }

    @Override
    public String toString() {
        return "idUser: " + idUser + "\n" +
                "fio: " + fio + "\n" +
                "login: " + login + "\n" +
                "group: " + group + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(idUser.get(), user.idUser.get()) &&
                Objects.equals(fio.get(), user.fio.get()) &&
                Objects.equals(login.get(), user.login.get()) &&
                Objects.equals(password.get(), user.password.get()) &&
                Objects.equals(dateReg.get(), user.dateReg.get()) &&
                Objects.equals(dateAuth.get(), user.dateAuth.get()) &&
                Objects.equals(phone.get(), user.phone.get()) &&
                Objects.equals(email.get(), user.email.get()) &&
                Objects.equals(status.get(), user.status.get()) &&
                Objects.equals(role.get(), user.role.get()) &&
                Objects.equals(group.get(), user.group.get()) &&
                Objects.equals(direction.get(), user.direction.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser.get(), fio.get(), login.get(), password.get(), dateReg.get(), dateAuth.get(), phone.get(), email.get(), status.get(), role.get(), group.get(), direction.get());
    }
}
