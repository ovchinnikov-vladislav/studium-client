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

    public RestPosition(HttpHeaders headers) {
        this.headers = headers;
        this.rest = new RestTemplate();
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public Position add(Position position) {
        Position newPosition = null;
        try {
            HttpEntity<Position> request = new HttpEntity<>(position, headers);
            newPosition = rest.exchange(url + URLPositionService.URL_ADD, HttpMethod.POST, request, Position.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return newPosition;
    }

    @Override
    public Position update(Position position) {
        Position updatePosition = null;
        try {
            HttpEntity<Position> request = new HttpEntity<>(position, headers);
            updatePosition = rest.exchange(url + URLPositionService.URL_UPDATE, HttpMethod.PUT, request, Position.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return updatePosition;
    }

    @Override
    public Position remove(Position position) {
        Position deletePosition = null;
        try {
            HttpEntity<Position> request = new HttpEntity<>(position, headers);
            deletePosition = rest.exchange(url + URLPositionService.URL_DELETE, HttpMethod.DELETE, request, Position.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return deletePosition;
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
