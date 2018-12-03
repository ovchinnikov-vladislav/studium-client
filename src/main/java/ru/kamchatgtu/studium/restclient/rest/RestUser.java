package ru.kamchatgtu.studium.restclient.rest;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import ru.kamchatgtu.studium.engine.Security;
import ru.kamchatgtu.studium.entity.user.User;
import ru.kamchatgtu.studium.restclient.AbstractRest;
import ru.kamchatgtu.studium.restclient.urlservice.URLUserService;

public class RestUser implements AbstractRest<User> {

    private RestTemplate rest;
    private HttpHeaders headers;
    private String url;

    public RestUser(HttpHeaders headers, String url) {
        this.headers = headers;
        this.url = url;
        this.rest = new RestTemplate();
    }

    @Override
    public User add(User user) {
        User newUser = null;
        try {
            HttpEntity<User> request = new HttpEntity<>(user, headers);
            newUser = rest.exchange(url + URLUserService.URL_ADD, HttpMethod.POST, request, User.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return newUser;
    }

    @Override
    public User update(User user) {
        User upUser = null;
        try {
            HttpEntity<User> request = new HttpEntity<>(user, headers);
            upUser = rest.exchange(url + URLUserService.URL_UPDATE, HttpMethod.PUT, request, User.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return upUser;
    }

    @Override
    public User remove(User user) {
        User delUser = null;
        try {
            HttpEntity<User> request = new HttpEntity<>(user, headers);
            delUser = rest.exchange(url + URLUserService.URL_DELETE, HttpMethod.DELETE, request, User.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return delUser;
    }

    @Override
    public User get(Integer id) {
        User user = null;
        try {
            HttpEntity<User> request = new HttpEntity<>(headers);
            user = rest.exchange(url + URLUserService.URL_USER + "?id=" + id, HttpMethod.GET, request, User.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return user;
    }

    @Override
    public ObservableList<User> getAll() {
        ObservableList<User> users = null;
        try {
            HttpEntity<User[]> request = new HttpEntity<>(headers);
            User[] usersArray = rest.exchange(url + URLUserService.URL_USERS, HttpMethod.GET, request, User[].class).getBody();
            if (usersArray != null) {
                users = FXCollections.observableArrayList();
                users.addAll(usersArray);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return users;
    }

    public boolean connect() {
        String result = null;
        try {
            HttpEntity<String> request = new HttpEntity<>(headers);
            result = rest.exchange(url + URLUserService.URL_LOGIN + "?login=admin", HttpMethod.GET, request, String.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return result != null;
    }

    public User login(String login, String password) {
        User user = null;
        try {
            HttpEntity<User> request = new HttpEntity<>(headers);
            user = rest.exchange(url + URLUserService.URL_LOGIN + "?login=" + login, HttpMethod.GET, request, User.class).getBody();
            if (user != null) {
                String passUser = user.getPassword();
                passUser = Security.decryptPass(passUser);
                if (!passUser.equals(password))
                    user = null;
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return user;
    }

    public User getUserByLogin(String login) {
        User user = null;
        try {
            HttpEntity<User> request = new HttpEntity<>(headers);
            user = rest.exchange(url + URLUserService.URL_USER_BY_LOGIN + "?login=" + login, HttpMethod.GET, request, User.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return user;
    }

    public User getUserByEmail(String email) {
        User user = null;
        try {
            HttpEntity<User> request = new HttpEntity<>(headers);
            user = rest.exchange(url + URLUserService.URL_USER_BY_EMAIL + "?email=" + email, HttpMethod.GET, request, User.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return user;
    }
}
