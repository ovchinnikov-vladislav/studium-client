package ru.kamchatgtu.studium.restclient.rest;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import ru.kamchatgtu.studium.entity.Role;
import ru.kamchatgtu.studium.restclient.AbstractRest;
import ru.kamchatgtu.studium.restclient.urlservice.URLRoleService;

public class RestRole implements AbstractRest<Role> {

    private RestTemplate rest;
    private HttpHeaders headers;
    private String url;

    public RestRole(HttpHeaders headers) {
        this.headers = headers;
        this.rest = new RestTemplate();
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public Role add(Role role) {
        Role newRole = null;
        try {
            HttpEntity<Role> request = new HttpEntity<>(role, headers);
            newRole = rest.exchange(url + URLRoleService.URL_ADD, HttpMethod.POST, request, Role.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return newRole;
    }

    @Override
    public Role update(Role role) {
        Role updateRole = null;
        try {
            HttpEntity<Role> request = new HttpEntity<>(role, headers);
            updateRole = rest.exchange(url + URLRoleService.URL_UPDATE, HttpMethod.PUT, request, Role.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return updateRole;
    }

    @Override
    public Role remove(Role role) {
        Role deleteRole = null;
        try {
            HttpEntity<Role> request = new HttpEntity<>(role, headers);
            deleteRole = rest.exchange(url + URLRoleService.URL_DELETE, HttpMethod.DELETE, request, Role.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return deleteRole;
    }

    @Override
    public Role get(int id) {
        Role role = null;
        try {
            HttpEntity<Role> request = new HttpEntity<>(headers);
            role = rest.exchange(url + URLRoleService.URL_ROLE + "?id=" + id, HttpMethod.GET, request, Role.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return role;
    }

    @Override
    public ObservableList<Role> getAll() {
        ObservableList<Role> roles = null;
        try {
            HttpEntity<Role[]> request = new HttpEntity<>(headers);
            Role[] positionsArray = rest.exchange(url + URLRoleService.URL_ROLES, HttpMethod.GET, request, Role[].class).getBody();
            if (positionsArray != null) {
                roles = FXCollections.observableArrayList();
                roles.addAll(positionsArray);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return roles;
    }
}
