package controllers;

import backEnd.PasswordsRepository;
import backEnd.UserRepository;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.Passwords;
import models.User;
import security.EncryptDecryptFile;
import security.EncryptDecryptPassword;

import java.io.FileNotFoundException;
import java.util.List;

public class MainWindowController {

    @FXML
    private Label passwordInDotsLabel;

    @FXML
    private Button hidePasswordButton;

    @FXML
    private Button updatedSelectedPasswordButton;

    @FXML
    private Pane updateSelectedPasswordPane;

    @FXML
    private TextField loginNameFieldForUpdating;

    @FXML
    private PasswordField passwordFieldForUpdating;

    @FXML
    private TextField urlFieldForUpdating;

    @FXML
    private TextArea moreInformationFieldForUpdating;

    @FXML
    private Label currentPasswordLabel;

    @FXML
    private Button showPasswordButton;

    @FXML
    private Button finallyUpdatePasswordButton;
    @FXML
    private Pane updatePasswordPane;

    @FXML
    private Button logOutButton;

    @FXML
    private Button newPasswordButton;

    @FXML
    private Button updatePasswordButton;

    @FXML
    private Button findPasswordButton;

    @FXML
    private Button deletePasswordButton;

    @FXML
    private Pane newPasswordPane;

    @FXML
    private TextField loginNameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField urlField;

    @FXML
    private TextArea moreInformationField;

    @FXML
    private Button createNewPasswordButton;

    @FXML
    private TableView<Passwords> passwordsListTable;

    @FXML
    private TableColumn<Passwords, String> loginName;

    @FXML
    private TableColumn<Passwords, String> password;

    @FXML
    private TableColumn<Passwords, String> url;

    @FXML
    private TableColumn<Passwords, String> moreInformation;

    PasswordsRepository passwordsRepository = new PasswordsRepository();
    UserRepository userRepository = new UserRepository();
    User user = userRepository.getUser();
    private List<Passwords> passwordsList;

    @FXML
    public void initialize() throws Exception {
        loginName.setCellValueFactory(new PropertyValueFactory<Passwords, String>("loginName"));
        password.setCellValueFactory(new PropertyValueFactory<Passwords, String>("password"));
        url.setCellValueFactory(new PropertyValueFactory<Passwords, String>("url"));
        moreInformation.setCellValueFactory(new PropertyValueFactory<Passwords, String>("moreInformation"));

        passwordsListTable.setItems(PasswordsRepository.getPasswordsObservableList(user.getUsername()));
    }

    public void newPasswordButtonClicked(ActionEvent actionEvent) {
        newPasswordPane.setVisible(!newPasswordPane.isVisible());
        updatePasswordPane.setVisible(false);
    }

    public void createNewPasswordButtonClicked(ActionEvent actionEvent) throws Exception {
        passwordsRepository.createNewPassword(loginNameField.getText(), passwordField.getText(),
                urlField.getText(), moreInformationField.getText(), user.getUsername() + ".txt");
        newPasswordPane.setVisible(false);
        loginNameField.clear();
        passwordField.clear();
        urlField.clear();
        moreInformationField.clear();
        passwordsListTable.setItems(FXCollections.observableList(PasswordsRepository.getPasswordsObservableList(user.getUsername())));
    }

    public void logOutButtonClicked(ActionEvent actionEvent) throws Exception {
        EncryptDecryptFile.encryptFile("UsersFiles\\" +  user.getUsername() + ".txt");
        userRepository.setUser(null);
        logOutButton.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../frontEnd/login&RegistrationWindow.fxml"));
        Parent parent = fxmlLoader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setTitle("Sign in | Sign up");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void updatePasswordButtonClicked(ActionEvent actionEvent) throws FileNotFoundException {
        updatePasswordPane.setVisible(!updatePasswordPane.isVisible());
        newPasswordPane.setVisible(false);
    }

    public void updatedSelectedPasswordButtonClicked(ActionEvent actionEvent) throws Exception {
        Passwords passwords = passwordsListTable.getSelectionModel().getSelectedItem();
        loginNameFieldForUpdating.setPromptText(passwords.getLoginName());
        urlFieldForUpdating.setPromptText(passwords.getUrl());
        moreInformationFieldForUpdating.setPromptText(passwords.getMoreInformation());
        currentPasswordLabel.setText(EncryptDecryptPassword.decryptPassword(passwords.getPassword()));
        updateSelectedPasswordPane.setVisible(!updateSelectedPasswordPane.isVisible());
    }

    public void showPasswordButtonClicked(ActionEvent actionEvent) {
        showPasswordButton.setVisible(!showPasswordButton.isVisible());
        passwordInDotsLabel.setVisible(!passwordInDotsLabel.isVisible());
        currentPasswordLabel.setVisible(!currentPasswordLabel.isVisible());
        hidePasswordButton.setVisible(!hidePasswordButton.isVisible());
    }

    public void finallyUpdatePasswordButtonClicked(ActionEvent actionEvent) throws Exception {
        Passwords passwords = passwordsListTable.getSelectionModel().getSelectedItem();
        PasswordsRepository.updatePassword(user.getUsername(), passwords.getPassword(), EncryptDecryptPassword.encryptPassword(passwordFieldForUpdating.getText()));
        updateSelectedPasswordPane.setVisible(!updateSelectedPasswordPane.isVisible());
        passwordsListTable.setItems(FXCollections.observableList(PasswordsRepository.getPasswordsObservableList(user.getUsername())));
        passwordFieldForUpdating.clear();
    }
}
