package ru.kamchatgtu.studium.restclient.rest;

import javafx.collections.ObservableList;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import ru.kamchatgtu.studium.entity.ResultQuestion;
import ru.kamchatgtu.studium.restclient.AbstractRest;

public class RestResultQuestion implements AbstractRest<ResultQuestion> {

    private RestTemplate rest;
    private HttpHeaders headers;
    private String url;

    public RestResultQuestion(HttpHeaders headers, String url) {
        this.headers = headers;
        this.url = url;
        this.rest = new RestTemplate();
    }

    @Override
    public ResultQuestion add(ResultQuestion resultQuestion) {
        return null;
    }

    @Override
    public ResultQuestion update(ResultQuestion resultQuestion) {
        return null;
    }

    @Override
    public ResultQuestion remove(ResultQuestion resultQuestion) {
        return null;
    }

    @Override
    public ResultQuestion get(Integer id) {
        return null;
    }

    @Override
    public ObservableList<ResultQuestion> getAll() {
        return null;
    }
}
