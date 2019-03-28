package ru.kamchatgtu.studium.restclient.rest;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import ru.kamchatgtu.studium.entity.ResultTest;
import ru.kamchatgtu.studium.entity.User;
import ru.kamchatgtu.studium.restclient.AbstractRest;
import ru.kamchatgtu.studium.restclient.urlservice.URLResultTestService;

public class RestResultTest implements AbstractRest<ResultTest> {

    private RestTemplate rest;
    private HttpHeaders headers;
    private String url;

    public RestResultTest(HttpHeaders headers) {
        this.headers = headers;
        this.rest = new RestTemplate();
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public ResultTest add(ResultTest resultTest) {
        ResultTest newResultTest = null;
        try {
            HttpEntity<ResultTest> request = new HttpEntity<>(resultTest, headers);
            newResultTest = rest.exchange(url + URLResultTestService.URL_ADD, HttpMethod.POST, request, ResultTest.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return newResultTest;
    }

    @Override
    public ResultTest update(ResultTest resultTest) {
        ResultTest updateResultTest = null;
        try {
            HttpEntity<ResultTest> request = new HttpEntity<>(resultTest, headers);
            updateResultTest = rest.exchange(url + URLResultTestService.URL_UPDATE, HttpMethod.PUT, request, ResultTest.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return updateResultTest;
    }

    @Override
    public ResultTest remove(ResultTest resultTest) {
        ResultTest deleteResultTest = null;
        try {
            HttpEntity<ResultTest> request = new HttpEntity<>(resultTest, headers);
            deleteResultTest = rest.exchange(url + URLResultTestService.URL_DELETE, HttpMethod.DELETE, request, ResultTest.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return deleteResultTest;
    }

    @Override
    public ResultTest get(Integer id) {
        ResultTest resultTest = null;
        try {
            HttpEntity<ResultTest> request = new HttpEntity<>(headers);
            resultTest = rest.exchange(url + URLResultTestService.URL_RESULT_TEST + "?id=" + id, HttpMethod.GET, request, ResultTest.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return resultTest;
    }

    @Override
    public ObservableList<ResultTest> getAll() {
        ObservableList<ResultTest> resultTests = null;
        try {
            HttpEntity<ResultTest[]> request = new HttpEntity<>(headers);
            ResultTest[] resultArray = rest.exchange(url + URLResultTestService.URL_RESULT_TESTS, HttpMethod.GET, request, ResultTest[].class).getBody();
            if (resultArray != null) {
                resultTests = FXCollections.observableArrayList();
                resultTests.addAll(resultArray);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return resultTests;
    }

    public ObservableList<ResultTest> getByUser(User user) {
        ObservableList<ResultTest> resultTests = null;
        try {
            HttpEntity<User> request = new HttpEntity<>(user, headers);
            ResultTest[] resultArray = rest.exchange(url + URLResultTestService.URL_RESULT_TESTS_BY_USER, HttpMethod.PUT, request, ResultTest[].class).getBody();
            if (resultArray != null) {
                resultTests = FXCollections.observableArrayList();
                resultTests.addAll(resultArray);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return resultTests;
    }

    public ObservableList<ResultTest> getByUserTests(User user) {
        ObservableList<ResultTest> resultTests = null;
        try {
            HttpEntity<User> request = new HttpEntity<>(user, headers);
            ResultTest[] resultArray = rest.exchange(url + URLResultTestService.URL_RESULT_TESTS_BY_USER_TESTS, HttpMethod.PUT, request, ResultTest[].class).getBody();
            if (resultArray != null) {
                resultTests = FXCollections.observableArrayList();
                resultTests.addAll(resultArray);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return resultTests;
    }

    public ResultTest fixResult(ResultTest resultTest) {
        ResultTest fixResultTest = null;
        try {
            HttpEntity<ResultTest> request = new HttpEntity<>(resultTest, headers);
            fixResultTest = rest.exchange(url + URLResultTestService.URL_FIX_RESULT, HttpMethod.PUT, request, ResultTest.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return fixResultTest;
    }
}
