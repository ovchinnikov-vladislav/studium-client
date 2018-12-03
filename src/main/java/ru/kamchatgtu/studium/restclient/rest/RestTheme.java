package ru.kamchatgtu.studium.restclient.rest;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import ru.kamchatgtu.studium.entity.Theme;
import ru.kamchatgtu.studium.restclient.AbstractRest;
import ru.kamchatgtu.studium.restclient.urlservice.URLThemeService;

public class RestTheme implements AbstractRest<Theme> {

    private RestTemplate rest;
    private HttpHeaders headers;
    private String url;

    public RestTheme(HttpHeaders headers, String url) {
        this.headers = headers;
        this.url = url;
        this.rest = new RestTemplate();
    }

    @Override
    public Theme add(Theme theme) {
        return null;
    }

    @Override
    public Theme update(Theme theme) {
        return null;
    }

    @Override
    public Theme remove(Theme theme) {
        return null;
    }

    @Override
    public Theme get(Integer id) {
        return null;
    }

    @Override
    public ObservableList<Theme> getAll() {
        ObservableList<Theme> themes = null;
        try {
            HttpEntity<String[]> request = new HttpEntity<>(headers);
            Theme[] themesArray = rest.exchange(url + URLThemeService.URL_THEMES, HttpMethod.GET, request, Theme[].class).getBody();
            if (themesArray != null) {
                themes = FXCollections.observableArrayList();
                themes.addAll(themesArray);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return themes;
    }
}
