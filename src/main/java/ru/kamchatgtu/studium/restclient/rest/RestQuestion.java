package ru.kamchatgtu.studium.restclient.rest;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import ru.kamchatgtu.studium.entity.Question;
import ru.kamchatgtu.studium.restclient.AbstractRest;
import ru.kamchatgtu.studium.restclient.urlservice.URLQuestionService;

public class RestQuestion implements AbstractRest<Question> {

    private RestTemplate rest;
    private HttpHeaders headers;
    private String url;

    public RestQuestion(HttpHeaders headers) {
        this.headers = headers;
        this.rest = new RestTemplate();
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public Question add(Question question) {
        Question newQuestion = null;
        try {
            HttpEntity<Question> request = new HttpEntity<>(question, headers);
            newQuestion = rest.exchange(url + URLQuestionService.URL_ADD, HttpMethod.POST, request, Question.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return newQuestion;
    }

    @Override
    public Question update(Question question) {
        Question updateQuestion = null;
        try {
            HttpEntity<Question> request = new HttpEntity<>(question, headers);
            updateQuestion = rest.exchange(url + URLQuestionService.URL_UPDATE, HttpMethod.PUT, request, Question.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return updateQuestion;
    }

    @Override
    public Question remove(Question question) {
        Question deleteQuestion = null;
        try {
            HttpEntity<Question> request = new HttpEntity<>(question, headers);
            deleteQuestion = rest.exchange(url + URLQuestionService.URL_DELETE, HttpMethod.DELETE, request, Question.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return deleteQuestion;
    }

    @Override
    public Question get(int id) {
        Question question = null;
        try {
            HttpEntity<Question> request = new HttpEntity<>(headers);
            question = rest.exchange(url + URLQuestionService.URL_QUESTION + "?id=" + id, HttpMethod.GET, request, Question.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return question;
    }

    @Override
    public ObservableList<Question> getAll() {
        ObservableList<Question> questions = null;
        try {
            HttpEntity<Question[]> request = new HttpEntity<>(headers);
            Question[] questionsArray = rest.exchange(url + URLQuestionService.URL_QUESTIONS, HttpMethod.GET, request, Question[].class).getBody();
            if (questionsArray != null) {
                questions = FXCollections.observableArrayList();
                questions.addAll(questionsArray);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return questions;
    }

    public ObservableList<Question> getQuestionsByTheme(Integer id) {
        ObservableList<Question> questions = null;
        try {
            HttpEntity<Question[]> request = new HttpEntity<>(headers);
            Question[] questionsArray = rest.exchange(url + URLQuestionService.URL_QUESTIONS_BY_THEME + "?id=" + id, HttpMethod.GET, request, Question[].class).getBody();
            if (questionsArray != null) {
                questions = FXCollections.observableArrayList();
                questions.addAll(questionsArray);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return questions;
    }

    public ObservableList<Question> search(Question question) {
        ObservableList<Question> questions = null;
        try {
            HttpEntity<Question> request = new HttpEntity<>(question, headers);
            Question[] questionsArray = rest.exchange(url + URLQuestionService.URL_SEARCH, HttpMethod.PUT, request, Question[].class).getBody();
            if (questionsArray != null) {
                questions = FXCollections.observableArrayList();
                questions.addAll(questionsArray);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return questions;
    }
}
