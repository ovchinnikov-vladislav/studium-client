package ru.kamchatgtu.studium.start;

import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.stage.Stage;
import ru.kamchatgtu.studium.engine.SecurityAES;
import ru.kamchatgtu.studium.restclient.RestConnection;
import ru.kamchatgtu.studium.view.connect.ConnectWindow;
import ru.kamchatgtu.studium.view.login.LoginWindow;
import ru.kamchatgtu.studium.view.preloader.SplashScreenLoader;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


public class Start extends Application {

    public static void main(String... args) {
        System.setProperty("prism.lcdtext", "false");
        System.setProperty("prism.text", "t2k");
        LauncherImpl.launchApplication(Start.class, SplashScreenLoader.class, args);
    }

    @Override
    public void start(Stage stage) {
        start();
    }

    private void start() {
        LoadTask call = new LoadTask();
        FutureTask<Boolean> futureTask = new FutureTask<>(call);
        Thread thread = new Thread(futureTask);
        thread.start();
        try {
            boolean flag = futureTask.get();
            if (flag) {
                Stage loginWindow = LoginWindow.getStage();
                loginWindow.show();
                this.notifyPreloader(new Preloader.ProgressNotification(1));
            } else {
                Stage connectWindow = ConnectWindow.getStage();
                connectWindow.show();
                this.notifyPreloader(new Preloader.ProgressNotification(1));
            }
        } catch (InterruptedException | ExecutionException | IOException exc) {
            exc.printStackTrace();
        }
    }

    public static class LoadTask implements Callable<Boolean> {

        @Override
        public Boolean call() {
            String dir = System.getProperty("user.dir");
            File file = new File(dir + "/data/connection.config");
            if (!file.exists()) {
                return false;
            } else {
                try {
                    RestConnection.setUrl(SecurityAES.readFile());
                    RestConnection restConnection = new RestConnection();
                    return restConnection.getRestUser().connect();
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            }
            return false;
        }
    }
}
