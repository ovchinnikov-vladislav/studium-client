package ru.kamchatgtu.studium.restclient.rest;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import ru.kamchatgtu.studium.entity.Group;
import ru.kamchatgtu.studium.restclient.AbstractRest;
import ru.kamchatgtu.studium.restclient.urlservice.URLGroupService;

public class RestGroup implements AbstractRest<Group> {

    private RestTemplate rest;
    private HttpHeaders headers;
    private String url;

    public RestGroup(HttpHeaders headers) {
        this.headers = headers;
        this.rest = new RestTemplate();
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public Group add(Group group) {
        Group newGroup = null;
        try {
            HttpEntity<Group> request = new HttpEntity<>(group, headers);
            newGroup = rest.exchange(url + URLGroupService.URL_ADD, HttpMethod.POST, request, Group.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return newGroup;
    }

    @Override
    public Group update(Group group) {
        Group updateGroup = null;
        try {
            HttpEntity<Group> request = new HttpEntity<>(group, headers);
            updateGroup = rest.exchange(url + URLGroupService.URL_UPDATE, HttpMethod.PUT, request, Group.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return updateGroup;
    }

    @Override
    public Group remove(Group group) {
        Group deleteGroup = null;
        try {
            HttpEntity<Group> request = new HttpEntity<>(group, headers);
            deleteGroup = rest.exchange(url + URLGroupService.URL_DELETE, HttpMethod.DELETE, request, Group.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return deleteGroup;
    }

    @Override
    public Group get(Integer id) {
        Group group = null;
        try {
            HttpEntity<Group> request = new HttpEntity<>(headers);
            group = rest.exchange(url + URLGroupService.URL_GROUP + "?id=" + id, HttpMethod.GET, request, Group.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return group;
    }

    @Override
    public ObservableList<Group> getAll() {
        ObservableList<Group> groups = null;
        try {
            HttpEntity<Group[]> request = new HttpEntity<>(headers);
            Group[] groupsArray = rest.exchange(url + URLGroupService.URL_GROUPS, HttpMethod.GET, request, Group[].class).getBody();
            if (groupsArray != null) {
                groups = FXCollections.observableArrayList();
                groups.addAll(groupsArray);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return groups;
    }

    public ObservableList<Group> getGroupsByRole(int id) {
        ObservableList<Group> groups = null;
        try {
            HttpEntity<Group[]> request = new HttpEntity<>(headers);
            Group[] groupsArray = rest.exchange(url + URLGroupService.URL_GROUPS_BY_ROLE + "?id=" + id, HttpMethod.GET, request, Group[].class).getBody();
            if (groupsArray != null) {
                groups = FXCollections.observableArrayList();
                groups.addAll(groupsArray);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return groups;
    }

    public ObservableList<Group> getGroupsByDirection(int id) {
        ObservableList<Group> groups = null;
        try {
            HttpEntity<Group[]> request = new HttpEntity<>(headers);
            Group[] groupsArray = rest.exchange(url + URLGroupService.URL_GROUPS_BY_DIRECTION + "?id=" + id, HttpMethod.GET, request, Group[].class).getBody();
            if (groupsArray != null) {
                groups = FXCollections.observableArrayList();
                groups.addAll(groupsArray);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return groups;
    }
}
