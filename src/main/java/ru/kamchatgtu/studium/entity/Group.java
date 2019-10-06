package ru.kamchatgtu.studium.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import ru.kamchatgtu.studium.restclient.RestConnection;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Group {

    private IntegerProperty idGroup;
    private StringProperty groupName;
    private ObjectProperty<Role> role;
    private ObjectProperty<Direction> direction;
    @JsonIgnore
    private Set<User> users;

    public Group() {
        this.idGroup = new SimpleIntegerProperty();
        this.groupName = new SimpleStringProperty();
        this.role = new SimpleObjectProperty<>();
        this.direction = new SimpleObjectProperty<>();
        users = new HashSet<>();
    }

    public int getIdGroup() {
        return idGroup.get();
    }

    public IntegerProperty idGroupProperty() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup.set(idGroup);
    }

    public String getGroupName() {
        return groupName.get();
    }

    public StringProperty groupNameProperty() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName.set(groupName);
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

    public Direction getDirection() {
        return direction.get();
    }

    public ObjectProperty<Direction> directionProperty() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction.set(direction);
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public void initUsers() {
        ObservableList<User> groupUsers = new RestConnection().getRestUser().getByGroup(getIdGroup());
        users.addAll(groupUsers);
    }

    @Override
    public String toString() {
        return groupName.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equals(idGroup.get(), group.idGroup.get()) &&
                Objects.equals(groupName.get(), group.groupName.get()) &&
                Objects.equals(role.get(), group.role.get()) &&
                Objects.equals(direction.get(), group.direction.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(idGroup.get(), groupName.get(), role.get(), direction.get());
    }
}
