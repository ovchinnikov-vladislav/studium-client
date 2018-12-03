package ru.kamchatgtu.studium.restclient.rest;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import ru.kamchatgtu.studium.entity.Answer;
import ru.kamchatgtu.studium.restclient.AbstractRest;
import ru.kamchatgtu.studium.restclient.urlservice.URLAnswerService;

public class RestAnswer implements AbstractRest<Answer> {

    private RestTemplate rest;
    private HttpHeaders headers;
    private String url;

    public RestAnswer(HttpHeaders headers, String url) {
        this.headers = headers;
        this.url = url;
        this.rest = new RestTemplate();
    }

    @Override
    public Answer add(Answer answer) {
        Answer newAnswer = null;
        try {
            HttpEntity<Answer> request = new HttpEntity<>(answer, headers);
            newAnswer = rest.exchange(url + URLAnswerService.URL_ADD, HttpMethod.POST, request, Answer.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return newAnswer;
    }

    @Override
    public Answer update(Answer answer) {
        Answer updateAnswer = null;
        try {
            HttpEntity<Answer> request = new HttpEntity<>(answer, headers);
            updateAnswer = rest.exchange(url + URLAnswerService.URL_UPDATE, HttpMethod.PUT, request, Answer.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return updateAnswer;
    }

    @Override
    public Answer remove(Answer answer) {
        Answer deleteAnswer = null;
        try {
            HttpEntity<Answer> request = new HttpEntity<>(answer, headers);
            deleteAnswer = rest.exchange(url + URLAnswerService.URL_DELETE, HttpMethod.DELETE, request, Answer.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return deleteAnswer;
    }

    @Override
    public Answer get(Integer id) {
        return null;
    }

    @Override
    public ObservableList<Answer> getAll() {
        return null;
    }

    public ObservableList<Answer> getAnswersByQuestion(Integer id) {
        ObservableList<Answer> answers = null;
        try {
            HttpEntity<Answer[]> request = new HttpEntity<>(headers);
            Answer[] answersArray = rest.exchange(url + URLAnswerService.URL_ANSWER_BY_QUESTION + "?id=" + id, HttpMethod.GET, request, Answer[].class).getBody();
            if (answersArray != null) {
                answers = FXCollections.observableArrayList();
                answers.addAll(answersArray);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return answers;
    }
}
