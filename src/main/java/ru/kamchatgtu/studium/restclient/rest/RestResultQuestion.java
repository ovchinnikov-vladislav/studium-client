package ru.kamchatgtu.studium.restclient.rest;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import ru.kamchatgtu.studium.entity.ResultQuestion;
import ru.kamchatgtu.studium.entity.ResultTest;
import ru.kamchatgtu.studium.restclient.AbstractRest;
import ru.kamchatgtu.studium.restclient.urlservice.URLResultQuestionService;

public class RestResultQuestion implements AbstractRest<ResultQuestion> {

    private RestTemplate rest;
    private HttpHeaders headers;
    private String url;

    public RestResultQuestion(HttpHeaders headers) {
        this.headers = headers;
        this.rest = new RestTemplate();
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public synchronized ResultQuestion add(ResultQuestion resultQuestion) {
        ResultQuestion newResultQuestion = null;
        try {
            HttpEntity<ResultQuestion> request = new HttpEntity<>(resultQuestion, headers);
            newResultQuestion = rest.exchange(url + URLResultQuestionService.URL_ADD, HttpMethod.POST, request, ResultQuestion.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return newResultQuestion;
    }

    @Override
    public synchronized ResultQuestion update(ResultQuestion resultQuestion) {
        ResultQuestion updateResultQuestion = null;
        try {
            HttpEntity<ResultQuestion> request = new HttpEntity<>(resultQuestion, headers);
            updateResultQuestion = rest.exchange(url + URLResultQuestionService.URL_UPDATE, HttpMethod.PUT, request, ResultQuestion.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return updateResultQuestion;
    }

    @Override
    public synchronized ResultQuestion remove(ResultQuestion resultQuestion) {
        ResultQuestion deleteResultQuestion = null;
        try {
            HttpEntity<ResultQuestion> request = new HttpEntity<>(resultQuestion, headers);
            deleteResultQuestion = rest.exchange(url + URLResultQuestionService.URL_DELETE, HttpMethod.DELETE, request, ResultQuestion.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return deleteResultQuestion;
    }

    @Override
    public synchronized ResultQuestion get(Integer id) {
        ResultQuestion resultQuestion = null;
        try {
            HttpEntity<ResultQuestion> request = new HttpEntity<>(headers);
            resultQuestion = rest.exchange(url + URLResultQuestionService.URL_RESULT_QUESTION + "?id=" + id, HttpMethod.GET, request, ResultQuestion.class).getBody();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return resultQuestion;
    }

    @Override
    public synchronized ObservableList<ResultQuestion> getAll() {
        ObservableList<ResultQuestion> resultQuestions = null;
        try {
            HttpEntity<ResultQuestion[]> request = new HttpEntity<>(headers);
            ResultQuestion[] resultArray = rest.exchange(url + URLResultQuestionService.URL_RESULT_QUESTIONS, HttpMethod.GET, request, ResultQuestion[].class).getBody();
            if (resultArray != null) {
                resultQuestions = FXCollections.observableArrayList();
                resultQuestions.addAll(resultArray);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return resultQuestions;
    }

    public synchronized ObservableList<ResultQuestion> getByResultTest(ResultTest resultTest) {
        ObservableList<ResultQuestion> resultQuestions = null;
        try {
            HttpEntity<ResultTest> request = new HttpEntity<>(resultTest, headers);
            ResultQuestion[] resultArray = rest.exchange(url + URLResultQuestionService.URL_RESULT_QUESTIONS_BY_RESULT_TEST, HttpMethod.POST, request, ResultQuestion[].class).getBody();
            if (resultArray != null) {
                resultQuestions = FXCollections.observableArrayList();
                resultQuestions.addAll(resultArray);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return resultQuestions;
    }

    public synchronized ObservableList<ResultQuestion> getByResultQuestion(ResultQuestion resultQuestion) {
        ObservableList<ResultQuestion> resultQuestions = null;
        try {
            HttpEntity<ResultQuestion> request = new HttpEntity<>(resultQuestion, headers);
            ResultQuestion[] resultArray = rest.exchange(url + URLResultQuestionService.URL_RESULT_QUESTIONS_BY_RESULT_QUESTION, HttpMethod.POST, request, ResultQuestion[].class).getBody();
            if (resultArray != null) {
                resultQuestions = FXCollections.observableArrayList();
                resultQuestions.addAll(resultArray);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return resultQuestions;
    }
}
