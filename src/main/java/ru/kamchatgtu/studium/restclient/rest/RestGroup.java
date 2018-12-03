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

    public RestGroup(HttpHeaders headers, String url) {
        this.headers = headers;
        this.url = url;
        this.rest = new RestTemplate();
    }

    @Override
    public Group add(Group group) {
        return null;
    }

    @Override
    public Group update(Group group) {
        return null;
    }

    @Override
    public Group remove(Group group) {
        return null;
    }

    @Override
    public Group get(Integer id) {
        return null;
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

    public ObservableList<Group> getGroupsByStudent() {
        ObservableList<Group> groups = null;
        try {
            HttpEntity<Group[]> request = new HttpEntity<>(headers);
            Group[] groupsArray = rest.exchange(url + URLGroupService.URL_GROUPS_BY_STUDENT_POSITION, HttpMethod.GET, request, Group[].class).getBody();
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
