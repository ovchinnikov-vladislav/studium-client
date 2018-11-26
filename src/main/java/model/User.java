package model;

import javafx.beans.property.*;

import java.util.Date;

public class User {

    private IntegerProperty ID = new SimpleIntegerProperty();
    private StringProperty fio = new SimpleStringProperty();
    private StringProperty login = new SimpleStringProperty();
    private ObjectProperty<Date> dateReg = new SimpleObjectProperty<>();
    private StringProperty phone = new SimpleStringProperty();
    private StringProperty email = new SimpleStringProperty();
    private IntegerProperty status = new SimpleIntegerProperty();
    private StringProperty position = new SimpleStringProperty();
    private IntegerProperty access = new SimpleIntegerProperty();
    private StringProperty group = new SimpleStringProperty();

    public User() {}

    public User(int ID, String fio, String login, Date dateReg, String phone, String email, int status, String position, int access, String group) {
        this.ID.set(ID);
        this.fio.set(fio);
        this.login.set(login);
        this.dateReg.set(dateReg);
        this.phone.set(phone);
        this.email.set(email);
        this.status.set(status);
        this.position.set(position);
        this.access.set(access);
        this.group.set(group);
    }

    public int getID() {
        return ID.get();
    }

    public IntegerProperty IDProperty() {
        return ID;
    }

    public void setID(int ID) {
        this.ID.set(ID);
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

    public Date getDateReg() {
        return dateReg.get();
    }

    public ObjectProperty<Date> dateRegProperty() {
        return dateReg;
    }

    public void setDateReg(Date dateReg) {
        this.dateReg.set(dateReg);
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

    public IntegerProperty statusProperty() {
        return status;
    }

    public void setStatus(int status) {
        this.status.set(status);
    }

    public String getPosition() {
        return position.get();
    }

    public StringProperty positionProperty() {
        return position;
    }

    public void setPosition(String positition) {
        this.position.set(positition);
    }

    public int getAccess() {
        return access.get();
    }

    public IntegerProperty accessProperty() {
        return access;
    }

    public void setAccess(int access) {
        this.access.set(access);
    }

    public String getGroup() {
        return group.get();
    }

    public StringProperty groupProperty() {
        return group;
    }

    public void setGroup(String group) {
        this.group.set(group);
    }

    @Override
    public String toString() {
        return "ID: " + getID() + "\n" +
                "FIO: " + getFio() + "\n" +
                "Login: " + getLogin() + "\n" +
                "DateReg: " + getDateReg()+ "\n" +
                "Phone: " + getPhone() + "\n" +
                "Email: " + getEmail() + "\n" +
                "Status: " + getStatus() + "\n" +
                "Position: " + getPosition() + "\n" +
                "Access: " + getAccess() + "\n";
    }
}
