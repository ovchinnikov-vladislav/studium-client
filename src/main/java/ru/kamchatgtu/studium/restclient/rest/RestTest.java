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

    public RestTest(HttpHeaders headers) {
        this.headers = headers;
        this.rest = new RestTemplate();
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public Test add(Test test) {
        Test newTest = null;
        try {
            HttpEntity<Test> request = new HttpEntity<>(test, headers);
            newTest = rest.exchange(url + URLTestService.URL_ADD, HttpMethod.POST, request, Test.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return newTest;
    }

    @Override
    public Test update(Test test) {
        Test updateTest = null;
        try {
            HttpEntity<Test> request = new HttpEntity<>(test, headers);
            updateTest = rest.exchange(url + URLTestService.URL_UPDATE, HttpMethod.PUT, request, Test.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return updateTest;
    }

    @Override
    public Test remove(Test test) {
        Test removeTest = null;
        try {
            HttpEntity<Test> request = new HttpEntity<>(test, headers);
            removeTest = rest.exchange(url + URLTestService.URL_DELETE, HttpMethod.DELETE, request, Test.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return removeTest;
    }

    @Override
    public Test get(Integer id) {
        Test test = null;
        try {
            HttpEntity<Test> request = new HttpEntity<>(headers);
            test = rest.exchange(url + URLTestService.URL_TEST + "?id=" + id, HttpMethod.GET, request, Test.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return test;
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
