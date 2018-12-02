package engine;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import model.User;
import view.connect.ConnectWindow;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;

public class Database {

    private static String url;
    private static String user;
    private static String password;

    private static Database instance;

    private Database() {
        try {
            Security.readFile();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    public static synchronized Database getInstance() {
        if (instance == null)
            instance = new Database();
        return instance;
    }


    public static void testConnection() throws SQLException {
        if (url == null || user == null) throw new SQLException();
        Connection connection = DriverManager.getConnection(url, user, password);
        connection.close();
    }

    public static ArrayList<String> getGroupsStudent() {
        ArrayList<String> groups = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("CALL get_group_students()")) {
            while (rs.next()) {
                groups.add(rs.getString(1));
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return groups;
    }

    public static ObservableList<String> getPositions() {
        ObservableList<String> positions = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("CALL get_positions()")) {
            while (rs.next()) {
                positions.add(rs.getString(1));
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return positions;
    }

    public static ObservableList<String> getGroups() {
        ObservableList<String> groups = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT `Group`.`name_group` FROM `Group`;")) {
            while (rs.next()) {
                groups.add(rs.getString(1));
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return groups;
    }

    public static ObservableList<User> getUsers() {
        /*ObservableList<User> users = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("call get_users()")) {
            while (rs.next()) {
                users.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getString(5),
                        rs.getString(6), rs.getInt(7), rs.getString(8), rs.getInt(9), rs.getString(10)));
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return users;*/
        return null;
    }

    public static void setUrl(String url) {
        Database.url = url;
    }

    public static void setUser(String user) {
        Database.user = user;
    }

    public static void setPassword(String password) {
        Database.password = password;
    }

    public static boolean getRightLoginPass(String login, String pass, User userLogin) {
        /*System.out.println(url + " " + user + " " + password);
        try (Connection connection = DriverManager.getConnection(url, user, password);
                Statement statement = connection.createStatement()) {
            String query = String.format("CALL login_user('%s', '%s')", login, Security.getMd5(pass));
            try (ResultSet rs = statement.executeQuery(query)) {
                if (rs.next()) {
                    userLogin.setIdUser(rs.getInt(1));
                    userLogin.setFio(rs.getString(2));
                    userLogin.setLogin(rs.getString(3));
                    userLogin.setDateReg(rs.getDate(4));
                    userLogin.setPhone(rs.getString(5));
                    userLogin.setEmail(rs.getString(6));
                    userLogin.setStatusPass(rs.getInt(7));
                    userLogin.setPosition(rs.getString(8));
                    userLogin.setAccess(rs.getInt(9));
                    userLogin.setGroup(rs.getString(10));
                    return true;
                }
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        }*/
        return false;
    }

    public static boolean addUserGroup(int access, String... args) {
        if (args.length == 8) {
            try (Connection connection = DriverManager.getConnection(url, user, password);
                 PreparedStatement statement = connection.prepareStatement("CALL add_user_group(?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
                statement.setString(1, args[0]);
                statement.setString(2, args[1]);
                statement.setString(3, args[2]);
                statement.setString(4, args[3]);
                statement.setString(5, args[4]);
                statement.setString(6, args[5]);
                statement.setString(7, args[6]);
                statement.setString(8, args[7]);
                statement.setInt(9, access);
                try (ResultSet rs = statement.executeQuery()) {
                    if (rs.next()) {
                        if (rs.getString(1).equals("Normal"))
                            return true;
                    }
                }
            }
            catch (SQLException exc) {
                exc.printStackTrace();
            }
        }
        return false;
    }
}
