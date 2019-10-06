package ru.kamchatgtu.studium.restclient.rest;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import ru.kamchatgtu.studium.entity.Log;
import ru.kamchatgtu.studium.restclient.AbstractRest;
import ru.kamchatgtu.studium.restclient.urlservice.URLLogService;

public class RestLog implements AbstractRest<Log> {

    private RestTemplate rest;
    private HttpHeaders headers;
    private String url;

    public RestLog(HttpHeaders headers) {
        this.headers = headers;
        this.rest = new RestTemplate();
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public Log add(Log log) {
        return null;
    }

    @Override
    public Log update(Log log) {
        return null;
    }

    @Override
    public Log remove(Log log) {
        return null;
    }

    @Override
    public Log get(int id) {
        Log log = null;
        try {
            HttpEntity<Log> request = new HttpEntity<>(headers);
            log = rest.exchange(url + URLLogService.URL_LOG + "?id=" + id, HttpMethod.GET, request, Log.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return log;
    }

    @Override
    public ObservableList<Log> getAll() {
        ObservableList<Log> logs = null;
        try {
            HttpEntity<Log[]> request = new HttpEntity<>(headers);
            Log[] logsArray = rest.exchange(url + URLLogService.URL_LOGS, HttpMethod.GET, request, Log[].class).getBody();
            if (logsArray != null) {
                logs = FXCollections.observableArrayList();
                logs.addAll(logsArray);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return logs;
    }

    public ObservableList<Log> search(Log log) {
        ObservableList<Log> logs = null;
        try {
            HttpEntity<Log> request = new HttpEntity<>(log, headers);
            Log[] logsArray = rest.exchange(url + URLLogService.URL_SEARCH, HttpMethod.PUT, request, Log[].class).getBody();
            if (logsArray != null) {
                logs = FXCollections.observableArrayList();
                logs.addAll(logsArray);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return logs;
    }
}
