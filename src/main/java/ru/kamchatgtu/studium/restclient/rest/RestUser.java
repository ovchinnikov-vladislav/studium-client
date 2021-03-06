package ru.kamchatgtu.studium.restclient.rest;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import ru.kamchatgtu.studium.engine.Security;
import ru.kamchatgtu.studium.entity.User;
import ru.kamchatgtu.studium.restclient.AbstractRest;
import ru.kamchatgtu.studium.restclient.urlservice.URLUserService;

public class RestUser implements AbstractRest<User> {

    private RestTemplate rest;
    private HttpHeaders headers;
    private String url;

    public RestUser(HttpHeaders headers) {
        this.headers = headers;
        this.rest = new RestTemplate();
    }

    public void setUrl(String url) {
        this.url = url;
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
    public User get(int id) {
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

    public ObservableList<User> search(User user) {
        ObservableList<User> users = null;
        try {
            HttpEntity<User> request = new HttpEntity<>(user, headers);
            User[] usersArray = rest.exchange(url + URLUserService.URL_SEARCH, HttpMethod.PUT, request, User[].class).getBody();
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
            result = rest.exchange(url + URLUserService.URL_SIGN_IN + "?login=admin", HttpMethod.GET, request, String.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return result != null;
    }

    public User login(String login, String password) {
        User user = null;
        try {
            HttpEntity<User> request = new HttpEntity<>(headers);
            user = rest.exchange(url + URLUserService.URL_SIGN_IN + "?login=" + login, HttpMethod.GET, request, User.class).getBody();
            if (user != null) {
                String passUser = user.getPassword();
                password = Security.encryptPass(password+user.getLogin());
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

    public ObservableList<User> getByGroup(int id) {
        ObservableList<User> users = null;
        try {
            HttpEntity<User[]> request = new HttpEntity<>(headers);
            User[] usersArray = rest.exchange(url + URLUserService.URL_USERS_BY_GROUP + "?id=" +id, HttpMethod.GET, request, User[].class).getBody();
            if (usersArray != null) {
                users = FXCollections.observableArrayList();
                users.addAll(usersArray);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return users;
    }

    public ObservableList<User> getStudents() {
        ObservableList<User> users = null;
        try {
            HttpEntity<User[]> request = new HttpEntity<>(headers);
            User[] usersArray = rest.exchange(url + URLUserService.URL_STUDENTS, HttpMethod.GET, request, User[].class).getBody();
            if (usersArray != null) {
                users = FXCollections.observableArrayList();
                users.addAll(usersArray);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return users;
    }

    public ObservableList<User> getTeachers() {
        ObservableList<User> users = null;
        try {
            HttpEntity<User[]> request = new HttpEntity<>(headers);
            User[] usersArray = rest.exchange(url + URLUserService.URL_TEACHERS, HttpMethod.GET, request, User[].class).getBody();
            if (usersArray != null) {
                users = FXCollections.observableArrayList();
                users.addAll(usersArray);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return users;
    }

    public ObservableList<User> getAdministrators() {
        ObservableList<User> users = null;
        try {
            HttpEntity<User[]> request = new HttpEntity<>(headers);
            User[] usersArray = rest.exchange(url + URLUserService.URL_ADMINISTRATORS, HttpMethod.GET, request, User[].class).getBody();
            if (usersArray != null) {
                users = FXCollections.observableArrayList();
                users.addAll(usersArray);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return users;
    }
}
