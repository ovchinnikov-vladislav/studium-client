package ru.kamchatgtu.studium.entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;

public class Role {

    private IntegerProperty idRole;
    private StringProperty roleName;
    private IntegerProperty access;

    public Role() {
        idRole = new SimpleIntegerProperty();
        roleName = new SimpleStringProperty();
        access = new SimpleIntegerProperty();
    }

    public int getIdRole() {
        return idRole.get();
    }

    public IntegerProperty idRoleProperty() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole.set(idRole);
    }

    public String getRoleName() {
        return roleName.get();
    }

    public StringProperty roleNameProperty() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName.set(roleName);
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

    @Override
    public String toString() {
        return roleName.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(idRole.get(), role.idRole.get()) &&
                Objects.equals(roleName.get(), role.roleName.get()) &&
                Objects.equals(access.get(), role.access.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRole.get(), roleName.get(), access.get());
    }
}
