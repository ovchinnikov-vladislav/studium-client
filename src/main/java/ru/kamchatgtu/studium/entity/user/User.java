package ru.kamchatgtu.studium.entity.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.*;
import ru.kamchatgtu.studium.entity.Group;
import ru.kamchatgtu.studium.entity.Position;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

    private IntegerProperty idUser;
    private StringProperty fio;
    private StringProperty login;
    private StringProperty password;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss a z")
    private ObjectProperty<Date> dateReg;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss a z")
    private ObjectProperty<Date> dateAuth;
    private StringProperty phone;
    private StringProperty email;
    private IntegerProperty status;
    private ObjectProperty<Group> group;
    private ObjectProperty<Position> position;

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
        group = new SimpleObjectProperty<>();
        group.setValue(new Group());
        position = new SimpleObjectProperty<>();
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
        setGroup(user.getGroup());
        setPosition(user.getPosition());
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

    public Group getGroup() {
        return group.get();
    }

    public ObjectProperty<Group> groupProperty() {
        return group;
    }

    public void setGroup(Group group) {
        this.group.set(group);
    }

    public Position getPosition() {
        return position.get();
    }

    public void setPosition(Position position) {
        this.position.set(position);
    }

    public ObjectProperty<Position> positionProperty() {
        return position;
    }

    @Override
    public String toString() {
        return "idUser: " + idUser + "\n" +
                "fio: " + fio + "\n" +
                "login: " + login + "\n" +
                "group: " + group + "\n";
    }

    @Override
    public boolean equals(Object user) {
        if (user == null) return false;
        if (!(user instanceof User)) return false;

        return ((User) user).idUser.equals(this.idUser) && ((User) user).dateReg.equals(this.dateReg) &&
                ((User) user).email.equals(this.email) && ((User) user).fio.equals(this.fio) &&
                ((User) user).login.equals(this.login) && ((User) user).password.equals(this.password) &&
                ((User) user).phone.equals(this.phone) && ((User) user).status.equals(status) &&
                ((User) user).dateAuth.equals(this.dateAuth);
    }
}
