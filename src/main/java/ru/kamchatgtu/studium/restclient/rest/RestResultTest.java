package ru.kamchatgtu.studium.restclient.rest;

import javafx.collections.ObservableList;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import ru.kamchatgtu.studium.entity.ResultTest;
import ru.kamchatgtu.studium.restclient.AbstractRest;

public class RestResultTest implements AbstractRest<ResultTest> {

    private RestTemplate rest;
    private HttpHeaders headers;
    private String url;

    public RestResultTest(HttpHeaders headers, String url) {
        this.headers = headers;
        this.url = url;
        this.rest = new RestTemplate();
    }

    @Override
    public ResultTest add(ResultTest resultTest) {
        return null;
    }

    @Override
    public ResultTest update(ResultTest resultTest) {
        return null;
    }

    @Override
    public ResultTest remove(ResultTest resultTest) {
        return null;
    }

    @Override
    public ResultTest get(Integer id) {
        return null;
    }

    @Override
    public ObservableList<ResultTest> getAll() {
        return null;
    }
}
