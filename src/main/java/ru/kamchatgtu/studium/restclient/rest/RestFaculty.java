package ru.kamchatgtu.studium.restclient.rest;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import ru.kamchatgtu.studium.entity.Faculty;
import ru.kamchatgtu.studium.restclient.AbstractRest;
import ru.kamchatgtu.studium.restclient.urlservice.URLFacultyService;

public class RestFaculty implements AbstractRest<Faculty> {

    private RestTemplate rest;
    private HttpHeaders headers;
    private String url;

    public RestFaculty(HttpHeaders headers) {
        this.headers = headers;
        this.rest = new RestTemplate();
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public Faculty add(Faculty faculty) {
        Faculty newFaculty = null;
        try {
            HttpEntity<Faculty> request = new HttpEntity<>(faculty, headers);
            newFaculty = rest.exchange(url + URLFacultyService.URL_ADD, HttpMethod.POST, request, Faculty.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return newFaculty;
    }

    @Override
    public Faculty update(Faculty faculty) {
        Faculty updateFaculty = null;
        try {
            HttpEntity<Faculty> request = new HttpEntity<>(faculty, headers);
            updateFaculty = rest.exchange(url + URLFacultyService.URL_UPDATE, HttpMethod.PUT, request, Faculty.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return updateFaculty;
    }

    @Override
    public Faculty remove(Faculty faculty) {
        Faculty deleteFaculty = null;
        try {
            HttpEntity<Faculty> request = new HttpEntity<>(faculty, headers);
            deleteFaculty = rest.exchange(url + URLFacultyService.URL_DELETE, HttpMethod.DELETE, request, Faculty.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return deleteFaculty;
    }

    @Override
    public Faculty get(Integer id) {
        Faculty faculty = null;
        try {
            HttpEntity<Faculty> request = new HttpEntity<>(headers);
            faculty = rest.exchange(url + URLFacultyService.URL_FACULTY + "?id=" + id, HttpMethod.GET, request, Faculty.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return faculty;
    }

    @Override
    public ObservableList<Faculty> getAll() {
        ObservableList<Faculty> faculties = null;
        try {
            HttpEntity<Faculty[]> request = new HttpEntity<>(headers);
            Faculty[] facultiesArray = rest.exchange(url + URLFacultyService.URL_FACULTIES, HttpMethod.GET, request, Faculty[].class).getBody();
            if (facultiesArray != null) {
                faculties = FXCollections.observableArrayList();
                faculties.addAll(facultiesArray);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return faculties;
    }
}
