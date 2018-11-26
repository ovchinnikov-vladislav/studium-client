package start;

import java.io.File;
import java.sql.SQLException;

import engine.Database;
import view.login.LoginWindow;
import view.connect.ConnectWindow;


public class Start {
    public static void main(String... args) {
        start();
    }

    private static void start() {
        String dir = System.getProperty("user.dir");
        File file = new File(dir + "/data/db.config");
        if (!file.exists()) {
            ConnectWindow connectWindow = new ConnectWindow();
            connectWindow.start();
        } else {
            Database database = Database.getInstance();
            LoginWindow loginWindow = new LoginWindow();
            loginWindow.start();
        }
    }
}
