package ru.kamchatgtu.studium.engine;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {

    private String userName;
    private String password;
    private Properties properties;

    public EmailSender(String userName, String password) {
        this.userName = userName;
        this.password = password;

        properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");
    }

    public static void main(String... args) {
        EmailSender emailSender = new EmailSender("testaccaunt170797@gmail.com", "RabbitVlad");
        emailSender.send("This is Subject", "This is message", "vladovchinnikov950@gmail.com");
    }

    public void send(String subject, String text, String toEmail) {
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(userName));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);
        } catch (MessagingException exc) {
            exc.printStackTrace();
        }
    }
}
