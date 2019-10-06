package ru.kamchatgtu.studium.restclient.rest;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import ru.kamchatgtu.studium.entity.Direction;
import ru.kamchatgtu.studium.entity.Group;
import ru.kamchatgtu.studium.entity.Subject;
import ru.kamchatgtu.studium.entity.User;
import ru.kamchatgtu.studium.restclient.AbstractRest;
import ru.kamchatgtu.studium.restclient.urlservice.URLSubjectService;

public class RestSubject implements AbstractRest<Subject> {

    private RestTemplate rest;
    private HttpHeaders headers;
    private String url;

    public RestSubject(HttpHeaders headers) {
        this.headers = headers;
        this.rest = new RestTemplate();
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public Subject add(Subject subject) {
        Subject newSubject = null;
        try {
            HttpEntity<Subject> request = new HttpEntity<>(subject, headers);
            newSubject = rest.exchange(url + URLSubjectService.URL_ADD, HttpMethod.POST, request, Subject.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return newSubject;
    }

    @Override
    public Subject update(Subject subject) {
        Subject updateSubj = null;
        try {
            HttpEntity<Subject> request = new HttpEntity<>(subject, headers);
            updateSubj = rest.exchange(url + URLSubjectService.URL_UPDATE, HttpMethod.PUT, request, Subject.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return updateSubj;
    }

    @Override
    public Subject remove(Subject subject) {
        Subject deleteSubj = null;
        try {
            HttpEntity<Subject> request = new HttpEntity<>(subject, headers);
            deleteSubj = rest.exchange(url + URLSubjectService.URL_DELETE, HttpMethod.DELETE, request, Subject.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return deleteSubj;
    }

    @Override
    public Subject get(int id) {
        Subject subject = null;
        try {
            HttpEntity<Subject> request = new HttpEntity<>(headers);
            subject = rest.exchange(url + URLSubjectService.URL_SUBJECT + "?id=" + id, HttpMethod.GET, request, Subject.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return subject;
    }

    @Override
    public ObservableList<Subject> getAll() {
        ObservableList<Subject> subjects = null;
        try {
            HttpEntity<Subject[]> request = new HttpEntity<>(headers);
            Subject[] subjectsArray = rest.exchange(url + URLSubjectService.URL_SUBJECTS, HttpMethod.GET, request, Subject[].class).getBody();
            if (subjectsArray != null) {
                subjects = FXCollections.observableArrayList();
                subjects.addAll(subjectsArray);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return subjects;
    }

    public ObservableList<Subject> getSubjectsWithTestsByUser(Integer id) {
        ObservableList<Subject> subjects = null;
        try {
            HttpEntity<Subject> request = new HttpEntity<>(headers);
            Subject[] subjectsArray = rest.exchange(url + URLSubjectService.URL_SUBJECTS_WITH_TESTS_BY_USER + "?id=" + id, HttpMethod.GET, request, Subject[].class).getBody();
            if (subjectsArray != null) {
                subjects = FXCollections.observableArrayList();
                subjects.addAll(subjectsArray);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return subjects;
    }

    public ObservableList<Subject> getByUser(Integer id) {
        ObservableList<Subject> subjects = null;
        try {
            HttpEntity<Subject> request = new HttpEntity<>(headers);
            Subject[] subjectsArray = rest.exchange(url + URLSubjectService.URL_SUBJECTS_BY_USER + "?id=" + id, HttpMethod.GET, request, Subject[].class).getBody();
            if (subjectsArray != null) {
                subjects = FXCollections.observableArrayList();
                subjects.addAll(subjectsArray);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return subjects;
    }

    public ObservableList<Subject> getByDirection(Integer id) {
        ObservableList<Subject> subjects = null;
        try {
            HttpEntity<Subject> request = new HttpEntity<>(headers);
            Subject[] subjectsArray = rest.exchange(url + URLSubjectService.URL_SUBJECTS_BY_DIRECTION+"?id="+id, HttpMethod.GET, request, Subject[].class).getBody();
            if (subjectsArray != null) {
                subjects = FXCollections.observableArrayList();
                subjects.addAll(subjectsArray);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return subjects;
    }
}
