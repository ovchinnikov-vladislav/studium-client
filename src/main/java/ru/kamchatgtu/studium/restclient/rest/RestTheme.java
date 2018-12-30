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
        Theme newTheme = null;
        try {
            HttpEntity<Theme> request = new HttpEntity<>(theme, headers);
            newTheme = rest.exchange(url + URLThemeService.URL_ADD, HttpMethod.POST, request, Theme.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return newTheme;
    }

    @Override
    public Theme update(Theme theme) {
        Theme updateTheme = null;
        try {
            HttpEntity<Theme> request = new HttpEntity<>(theme, headers);
            updateTheme = rest.exchange(url + URLThemeService.URL_UPDATE, HttpMethod.PUT, request, Theme.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return updateTheme;
    }

    @Override
    public Theme remove(Theme theme) {
        Theme removeTheme = null;
        try {
            HttpEntity<Theme> request = new HttpEntity<>(theme, headers);
            removeTheme = rest.exchange(url + URLThemeService.URL_DELETE, HttpMethod.DELETE, request, Theme.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return removeTheme;
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
