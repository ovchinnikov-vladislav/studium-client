package engine;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import entity.Group;
import entity.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class RestConnection {

    private String url;
    private HttpHeaders headers;
    private RestTemplate rest;
    private static RestConnection restConnection = null;

    private RestConnection() {
        headers = new HttpHeaders();
        rest = new RestTemplate();
        headers.setAccept(Arrays.asList(new MediaType[]{MediaType.APPLICATION_JSON}));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("my_other_key", "my_other_value");
    }

    public static synchronized RestConnection getInstance() {
        if (restConnection == null)
            restConnection = new RestConnection();
        return restConnection;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean connect() {
        int i = 0;
        while (true) {
            try {
                i++;
                if (i < 3) {
                    HttpEntity<String> entity = new HttpEntity<>(headers);
                    RestTemplate restTemplate = new RestTemplate();
                    ResponseEntity<String> response = restTemplate.exchange(url + "/GroupService/group?id=1", HttpMethod.GET, entity, String.class);
                    System.out.println(response.getBody());
                    return true;
                } else return false;
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
    }

    public User login(String login, String password) {
        int i = 0;
        while (true) {
            try {
                i++;
                if (i < 100) {
                    HttpEntity<User> entity = new HttpEntity<>(headers);
                    RestTemplate restTemplate = new RestTemplate();
                    ResponseEntity<User> response = restTemplate.exchange(url + "/UserService/enter?login=" + login + "&password=" + Security.getMd5(password), HttpMethod.GET, entity, User.class);
                    User user = response.getBody();
                    if (!user.getLogin().isEmpty() && !user.getPassword().isEmpty())
                        return response.getBody();
                } else return null;
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
    }

    public User getUser(Integer id) {
        while (true) {
            try {
                    HttpEntity<User> entity = new HttpEntity<>(headers);
                    RestTemplate restTemplate = new RestTemplate();
                    ResponseEntity<User> response = restTemplate.exchange(url + "/UserService/user?id="+id, HttpMethod.GET, entity, User.class);
                    User user = response.getBody();
                    if (!user.getLogin().isEmpty() && !user.getPassword().isEmpty())
                        return response.getBody();
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
    }

    public User[] getUsers() {
        while (true) {
            try {
                HttpEntity<User[]> entity = new HttpEntity<>(headers);
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<User[]> response = restTemplate.exchange(url + "/UserService/users", HttpMethod.GET, entity, User[].class);
                User[] users = response.getBody();
                if (users != null && users.length > 0)
                    return users;
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
    }

    public ObservableList<String> getGroups() {
        while (true) {
            try {
                HttpEntity<Group[]> entity = new HttpEntity<>(headers);
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<Group[]> response = restTemplate.exchange(url + "/GroupService/groups", HttpMethod.GET, entity, Group[].class);
                Group[] groups = response.getBody();
                ObservableList<String> strings = FXCollections.observableArrayList();
                for (Group group : groups)
                    strings.add(group.getNameGroup());
                if (groups != null && groups.length > 0)
                    return strings;
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
    }
}
