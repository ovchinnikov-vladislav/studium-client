package ru.kamchatgtu.studium.restclient.rest;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import ru.kamchatgtu.studium.entity.Direction;
import ru.kamchatgtu.studium.restclient.AbstractRest;
import ru.kamchatgtu.studium.restclient.urlservice.URLDirectionService;

public class RestDirection implements AbstractRest<Direction> {

    private RestTemplate rest;
    private HttpHeaders headers;
    private String url;

    public RestDirection(HttpHeaders headers) {
        this.headers = headers;
        this.rest = new RestTemplate();
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public Direction add(Direction direction) {
        Direction newDirection = null;
        try {
            HttpEntity<Direction> request = new HttpEntity<>(direction, headers);
            newDirection = rest.exchange(url + URLDirectionService.URL_ADD, HttpMethod.POST, request, Direction.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return newDirection;
    }

    @Override
    public Direction update(Direction direction) {
        Direction updateDirection = null;
        try {
            HttpEntity<Direction> request = new HttpEntity<>(direction, headers);
            updateDirection = rest.exchange(url + URLDirectionService.URL_UPDATE, HttpMethod.PUT, request, Direction.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return updateDirection;
    }

    @Override
    public Direction remove(Direction direction) {
        Direction deleteDirection = null;
        try {
            HttpEntity<Direction> request = new HttpEntity<>(direction, headers);
            deleteDirection = rest.exchange(url + URLDirectionService.URL_DELETE, HttpMethod.DELETE, request, Direction.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return deleteDirection;
    }

    @Override
    public Direction get(int id) {
        Direction direction = null;
        try {
            HttpEntity<Direction> request = new HttpEntity<>(headers);
            direction = rest.exchange(url + URLDirectionService.URL_DIRECTION + "?id=" + id, HttpMethod.GET, request, Direction.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return direction;
    }

    @Override
    public ObservableList<Direction> getAll() {
        ObservableList<Direction> directions = null;
        try {
            HttpEntity<Direction[]> request = new HttpEntity<>(headers);
            Direction[] directionsArray = rest.exchange(url + URLDirectionService.URL_DIRECTIONS, HttpMethod.GET, request, Direction[].class).getBody();
            if (directionsArray != null) {
                directions = FXCollections.observableArrayList();
                directions.addAll(directionsArray);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return directions;
    }

    public ObservableList<Direction> getByFaculty(Integer id) {
        ObservableList<Direction> directions = null;
        try {
            HttpEntity<Direction[]> request = new HttpEntity<>(headers);
            Direction[] directionsArray = rest.exchange(url + URLDirectionService.URL_DIRECTIONS_BY_FACULTY + "?id=" + id, HttpMethod.GET, request, Direction[].class).getBody();
            if (directionsArray != null) {
                directions = FXCollections.observableArrayList();
                directions.addAll(directionsArray);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return directions;
    }
}
