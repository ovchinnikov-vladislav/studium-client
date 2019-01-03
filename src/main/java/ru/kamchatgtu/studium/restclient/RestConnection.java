package ru.kamchatgtu.studium.restclient;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpHeaders;
import ru.kamchatgtu.studium.restclient.rest.*;

public class RestConnection {

    private static String url;
    private HttpHeaders headers;

    private RestUser restUser = null;
    private RestTest restTest = null;
    private RestSubject restSubject = null;
    private RestResultTest restResultTest = null;
    private RestResultQuestion restResultQuestion = null;
    private RestQuestion restQuestion = null;
    private RestPosition restPosition = null;
    private RestGroup restGroup = null;
    private RestAnswer restAnswer = null;
    private RestTheme restTheme = null;

    public RestConnection() {
        headers = createHeaders("administrator", "hardpassword");
    }

    public static void setUrl(String url) {
        RestConnection.url = url;
    }

    private HttpHeaders createHeaders(String username, String password) {
        String plainCredentials = username + ":" + password;
        String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        headers.add("Content-Type", "application/json; charset=utf-8");
        return headers;
    }

    public synchronized RestUser getRestUser() {
        if (restUser == null && url != null)
            restUser = new RestUser(headers);
        restUser.setUrl(url);
        return restUser;
    }

    public synchronized RestTest getRestTest() {
        if (restTest == null && url != null)
            restTest = new RestTest(headers);
        restTest.setUrl(url);
        return restTest;
    }

    public synchronized RestSubject getRestSubject() {
        if (restSubject == null && url != null)
            restSubject = new RestSubject(headers);
        restSubject.setUrl(url);
        return restSubject;
    }

    public synchronized RestResultTest getRestResultTest() {
        if (restResultTest == null && url != null)
            restResultTest = new RestResultTest(headers);
        restResultTest.setUrl(url);
        return restResultTest;
    }

    public synchronized RestResultQuestion getRestResultQuestion() {
        if (restResultQuestion == null && url != null)
            restResultQuestion = new RestResultQuestion(headers);
        restResultQuestion.setUrl(url);
        return restResultQuestion;
    }

    public synchronized RestQuestion getRestQuestion() {
        if (restQuestion == null && url != null)
            restQuestion = new RestQuestion(headers);
        restQuestion.setUrl(url);
        return restQuestion;
    }

    public synchronized RestPosition getRestPosition() {
        if (restPosition == null && url != null)
            restPosition = new RestPosition(headers);
        restPosition.setUrl(url);
        return restPosition;
    }

    public synchronized RestGroup getRestGroup() {
        if (restGroup == null && url != null)
            restGroup = new RestGroup(headers);
        restGroup.setUrl(url);
        return restGroup;
    }

    public synchronized RestAnswer getRestAnswer() {
        if (restAnswer == null && url != null)
            restAnswer = new RestAnswer(headers);
        restAnswer.setUrl(url);
        return restAnswer;
    }

    public synchronized RestTheme getRestTheme() {
        if (restTheme == null && url != null)
            restTheme = new RestTheme(headers);
        restTheme.setUrl(url);
        return restTheme;
    }
}
