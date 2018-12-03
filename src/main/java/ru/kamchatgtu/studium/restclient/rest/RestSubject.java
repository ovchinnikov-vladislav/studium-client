package ru.kamchatgtu.studium.restclient.rest;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import ru.kamchatgtu.studium.entity.Subject;
import ru.kamchatgtu.studium.restclient.AbstractRest;
import ru.kamchatgtu.studium.restclient.urlservice.URLSubjectService;

public class RestSubject implements AbstractRest<Subject> {

    private RestTemplate rest;
    private HttpHeaders headers;
    private String url;

    public RestSubject(HttpHeaders headers, String url) {
        this.headers = headers;
        this.url = url;
        this.rest = new RestTemplate();
    }

    @Override
    public Subject add(Subject subject) {
        return null;
    }

    @Override
    public Subject update(Subject subject) {
        return null;
    }

    @Override
    public Subject remove(Subject subject) {
        return null;
    }

    @Override
    public Subject get(Integer id) {
        return null;
    }

    @Override
    public ObservableList<Subject> getAll() {
        ObservableList<Subject> subjects = null;
        try {
            HttpEntity<Subject[]> request = new HttpEntity<>(headers);
            Subject[] subjectsArray = rest.exchange(url + URLSubjectService.URL_SUBJECTS, HttpMethod.GET, request, Subject[].class).getBody();
            if (subjectsArray != null) {
                subjects = FXCollections.observableArrayList();
                subjects.addAll(subjectsArray);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return subjects;
    }
}
