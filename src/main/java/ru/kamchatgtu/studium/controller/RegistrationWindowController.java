package ru.kamchatgtu.studium.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import ru.kamchatgtu.studium.component.TextFieldTooltipPattern;
import ru.kamchatgtu.studium.engine.Security;
import ru.kamchatgtu.studium.engine.thread.LoginAsync;
import ru.kamchatgtu.studium.engine.thread.RegistrationAsync;
import ru.kamchatgtu.studium.engine.thread.TestEmailAsync;
import ru.kamchatgtu.studium.engine.thread.TestLoginAsync;
import ru.kamchatgtu.studium.entity.*;
import ru.kamchatgtu.studium.restclient.RestConnection;

import java.sql.Timestamp;
import java.util.Date;
import java.util.regex.Pattern;

public class RegistrationWindowController {

    private ObservableList<Faculty> faculties;
    private ObservableList<Direction> directions;
    private ObservableList<Group> groups;

    @FXML
    private GridPane borderLogin;
    @FXML
    private TextField loginField;
    @FXML
    private TextField passField;
    @FXML
    private Button logButton;
    @FXML
    private ProgressIndicator progressLogIndicator;
    @FXML
    private Label errorLabel;

    @FXML
    private GridPane borderReg;
    @FXML
    private TextField fioRegField;
    @FXML
    private TextField loginRegField;
    @FXML
    private TextField passRegField;
    @FXML
    private ComboBox<Faculty> facultyBox;
    @FXML
    private ComboBox<Direction> directionBox;
    @FXML
    private ComboBox<Group> groupBox;
    @FXML
    private CheckBox groupCheckBox;
    @FXML
    private TextField groupRegField;
    @FXML
    private TextField emailRegField;
    @FXML
    private TextField phoneField;
    @FXML
    private Button regButton;
    @FXML
    private ProgressIndicator progressRegIndicator;

    private Tooltip loginRegTooltip, emailRegTooltip, fioRegTooltip;

    @FXML
    public void initialize() {
        setGroupCheckBoxHandler();
        initFaculties();
        initDirections();
       // initFioRegField();
     //   initLoginRegField();
       // initEmailRegField();
    }

    private void initFaculties() {
        faculties = new RestConnection().getRestFaculty().getAll();
        facultyBox.setItems(faculties);
        facultyBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            directions = new RestConnection().getRestDirection().getByFaculty(faculties.get(newValue.intValue()).getIdFaculty());
            directionBox.setItems(directions);
            groupBox.setDisable(true);
            groupCheckBox.setDisable(true);
            groupRegField.setVisible(false);
            groupCheckBox.setSelected(false);
            groupBox.setVisible(true);
            if (directions != null && directions.size() > 0) {
                directionBox.setDisable(false);
            } else {
                directionBox.setDisable(true);
            }
        });
    }

    private void initDirections() {
        directionBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            groups = new RestConnection().getRestGroup().getGroupsByDirection(directions.get(newValue.intValue()).getIdDirection());
            groupBox.setItems(groups);
            if (groups != null && groups.size() > 0) {
                groupBox.setDisable(false);
                groupCheckBox.setDisable(false);
                groupBox.setVisible(true);
                groupRegField.setVisible(false);
                groupCheckBox.setSelected(false);
            } else {
                groupBox.setVisible(false);
                groupRegField.setVisible(true);
                groupCheckBox.setSelected(true);
                groupCheckBox.setDisable(true);
            }
        });
    }

    private void initFioRegField() {
        Pattern pattern = Pattern.compile("[A-zА-я]");
        fioRegTooltip = new Tooltip("ФИО может состоять только\nиз символов алфавита");
        TextFieldTooltipPattern.initField(fioRegField, fioRegTooltip, pattern);
    }

    private void initLoginRegField() {
        Pattern pattern = Pattern.compile("[A-z0-9]{0,16}");
        loginRegTooltip = new Tooltip("Логин может состоять только из символов латинского\nалфавита и цифр (максимальная длина 16)");
        initField(loginRegField, loginRegTooltip, pattern);
    }

    private void initEmailRegField() {
        Pattern pattern = Pattern.compile("^([a-z0-9_.-]*)@([a-z0-9_.-]*).([a-z.]{2,6})$");
        emailRegTooltip = new Tooltip("Электронный адрес должен соответствовать\nформату: email@address.domain");
        initField(emailRegField, emailRegTooltip, pattern);
    }

    public void initField(TextField textField, Tooltip tooltip, Pattern pattern) {
        tooltip.setWrapText(true);
        textField.focusedProperty().addListener(observable -> tooltip.hide());
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!pattern.matcher(newValue).matches()) {
                
                tooltip.show(textField,
                        textField.getScene().getWindow().getX() + textField.getLayoutX() + textField.getWidth() + 10,
                        textField.getScene().getWindow().getY() + textField.getLayoutY() + textField.getHeight());
            } else {
                if (textField.equals(emailRegField)) {
                    TestEmailAsync testEmailAsync = new TestEmailAsync(textField);
                    testEmailAsync.execute();
                } else if (textField.equals(loginRegField)) {
                    TestLoginAsync testLoginAsync = new TestLoginAsync(loginRegField);
                    testLoginAsync.execute();
                }
            }
        });
    }

    @FXML
    public void loginKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER)
            passField.requestFocus();
    }

    @FXML
    public void passKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER)
            login(loginField.getText(), passField.getText());
    }

    @FXML
    public void loginAction(ActionEvent event) {
        login(loginField.getText(), passField.getText());
    }

    private void login(String login, String pass) {
        LoginAsync task = new LoginAsync(logButton, progressLogIndicator, errorLabel, login, pass);
        task.execute();
    }

    @FXML
    public void fioRegKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER)
            loginRegField.requestFocus();
    }

    @FXML
    public void loginRegKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER)
            passRegField.requestFocus();
    }

    @FXML
    public void passRegKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER)
            emailRegField.requestFocus();
    }

    @FXML
    public void emailRegKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER)
            phoneField.requestFocus();
    }

    @FXML
    public void phoneKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            registration();
        }
    }

    @FXML
    public void regButtonAction(ActionEvent event) {
        registration();
    }

    private void registration() {
        User newUser = new User();
        Role role = new Role();
        role.setAccess(3);
        role.setIdRole(3);
        role.setRoleName("Студент");
        Direction direction = directionBox.getSelectionModel().getSelectedItem();
        newUser.setStatus(3);
        newUser.setFio(fioRegField.getText());
        newUser.setLogin(loginRegField.getText());
        newUser.setPassword(Security.encryptPass(passRegField.getText()+loginRegField.getText()));
        newUser.setEmail(emailRegField.getText());
        newUser.setPhone(phoneField.getText());
        newUser.setDirection(direction);
        if (!groupCheckBox.isSelected())
            newUser.setGroup(groupBox.getValue());
        else {
            Group group = new Group();
            group.setDirection(direction);
            group.setRole(role);
            group.setGroupName(groupRegField.getText());
            group = new RestConnection().getRestGroup().add(group);
            newUser.setGroup(group);
        }
        newUser.setDateReg(new Timestamp(new Date().getTime()));
        newUser.setDateAuth(new Timestamp(new Date().getTime()));
        newUser.setRole(role);
        RegistrationAsync registrationAsync = new RegistrationAsync(regButton, progressRegIndicator, newUser);
        registrationAsync.execute();
    }

    private void setGroupCheckBoxHandler() {
        groupCheckBox.selectedProperty().addListener((obs, oldValue, newValue) -> {
            groupBox.setVisible(!newValue);
            groupRegField.setVisible(newValue);
        });
    }

    @FXML
    public void loginMenuClick(ActionEvent event) {
        borderLogin.setVisible(true);
        borderReg.setVisible(false);
    }

    @FXML
    public void regMenuClick(ActionEvent event) {
        borderReg.setVisible(true);
        borderLogin.setVisible(false);
    }

}
