package start;

import java.io.File;
import java.sql.SQLException;

import engine.Database;
import engine.RestConnection;
import engine.Security;
import view.login.LoginWindow;
import view.connect.ConnectWindow;


public class Start {
    public static void main(String... args) {
        start();
    }

    private static void start() {
        String dir = System.getProperty("user.dir");
        File file = new File(dir + "/data/connection.config");
        if (!file.exists()) {
            ConnectWindow connectWindow = new ConnectWindow();
            connectWindow.start();
        } else {
            try {
                RestConnection restConnection = RestConnection.getInstance();
                restConnection.setUrl(Security.readFile());
                LoginWindow loginWindow = new LoginWindow();
                loginWindow.start();
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
    }
}
