package ru.kamchatgtu.studium.restclient.rest;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import ru.kamchatgtu.studium.entity.Position;
import ru.kamchatgtu.studium.restclient.AbstractRest;
import ru.kamchatgtu.studium.restclient.urlservice.URLPositionService;

public class RestPosition implements AbstractRest<Position> {

    private RestTemplate rest;
    private HttpHeaders headers;
    private String url;

    public RestPosition(HttpHeaders headers, String url) {
        this.headers = headers;
        this.url = url;
        this.rest = new RestTemplate();
    }

    @Override
    public Position add(Position position) {
        return null;
    }

    @Override
    public Position update(Position position) {
        return null;
    }

    @Override
    public Position remove(Position position) {
        return null;
    }

    @Override
    public Position get(Integer id) {
        Position position = null;
        try {
            HttpEntity<Position> request = new HttpEntity<>(headers);
            position = rest.exchange(url + URLPositionService.URL_POSITION + "?id=" + id, HttpMethod.GET, request, Position.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return position;
    }

    @Override
    public ObservableList<Position> getAll() {
        ObservableList<Position> positions = null;
        try {
            HttpEntity<Position[]> request = new HttpEntity<>(headers);
            Position[] positionsArray = rest.exchange(url + URLPositionService.URL_POSITIONS, HttpMethod.GET, request, Position[].class).getBody();
            if (positionsArray != null) {
                positions = FXCollections.observableArrayList();
                positions.addAll(positionsArray);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return positions;
    }
}
