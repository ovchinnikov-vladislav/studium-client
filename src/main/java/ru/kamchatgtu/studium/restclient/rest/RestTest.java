package ru.kamchatgtu.studium.restclient.rest;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import ru.kamchatgtu.studium.entity.Test;
import ru.kamchatgtu.studium.restclient.AbstractRest;
import ru.kamchatgtu.studium.restclient.urlservice.URLTestService;

public class RestTest implements AbstractRest<Test> {

    private RestTemplate rest;
    private HttpHeaders headers;
    private String url;

    public RestTest(HttpHeaders headers, String url) {
        this.headers = headers;
        this.url = url;
        this.rest = new RestTemplate();
    }

    @Override
    public Test add(Test test) {
        return null;
    }

    @Override
    public Test update(Test test) {
        return null;
    }

    @Override
    public Test remove(Test test) {
        return null;
    }

    @Override
    public Test get(Integer id) {
        return null;
    }

    @Override
    public ObservableList<Test> getAll() {
        ObservableList<Test> tests = null;
        try {
            HttpEntity<Test[]> request = new HttpEntity<>(headers);
            Test[] testsArray = rest.exchange(url + URLTestService.URL_TESTS, HttpMethod.GET, request, Test[].class).getBody();
            if (testsArray != null) {
                tests = FXCollections.observableArrayList();
                tests.addAll(testsArray);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return tests;
    }

    public ObservableList<Test> getTestsBySubject(Integer idSubject) {
        ObservableList<Test> tests = null;
        try {
            HttpEntity<Test[]> request = new HttpEntity<>(headers);
            Test[] testsArray = rest.exchange(url + URLTestService.URL_TESTS_BY_SUBJECT + "?id=" + idSubject, HttpMethod.GET, request, Test[].class).getBody();
            if (testsArray != null) {
                tests = FXCollections.observableArrayList();
                tests.addAll(testsArray);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return tests;
    }
}
