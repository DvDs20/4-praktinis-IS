package controllers;

import backEnd.PasswordsRepository;
import backEnd.UserRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.Passwords;
import models.User;
import security.EncryptDecryptFile;
import security.EncryptDecryptPassword;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class MainWindowController {

    @FXML
    private Button copyPasswordButton;

    @FXML
    private Pane findPasswordPane;

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

    @FXML
    private TableView<Passwords> findPasswordTable;

    @FXML
    private TableColumn<Passwords, String> loginNameForFindPassword;

    @FXML
    private TableColumn<Passwords, Button> passwordForFindPassword;

    @FXML
    private TableColumn<Passwords, String> urlForFindPassword;

    @FXML
    private TableColumn<Passwords, String> moreInformationForFindPassword;

    PasswordsRepository passwordsRepository = new PasswordsRepository();
    UserRepository userRepository = new UserRepository();
    User user = userRepository.getUser();
    private List<Passwords> passwordsList;
    List<Passwords> passwordsList1 = PasswordsRepository.getPasswordsObservableListForFindPassword(user.getUsername());
    Button[] buttons;

    @FXML
    public void initialize() throws Exception {
        loginName.setCellValueFactory(new PropertyValueFactory<Passwords, String>("loginName"));
        password.setCellValueFactory(new PropertyValueFactory<Passwords, String>("password"));
        url.setCellValueFactory(new PropertyValueFactory<Passwords, String>("url"));
        moreInformation.setCellValueFactory(new PropertyValueFactory<Passwords, String>("moreInformation"));

        passwordsListTable.setItems(PasswordsRepository.getPasswordsObservableList(user.getUsername()));

        loginNameForFindPassword.setCellValueFactory(new PropertyValueFactory<Passwords, String>("loginName"));
        passwordForFindPassword.setCellValueFactory(new PropertyValueFactory<Passwords, Button>("button"));
        urlForFindPassword.setCellValueFactory(new PropertyValueFactory<Passwords, String>("url"));
        moreInformationForFindPassword.setCellValueFactory(new PropertyValueFactory<Passwords, String>("moreInformation"));

    }

    public void newPasswordButtonClicked(ActionEvent actionEvent) {
        newPasswordPane.setVisible(!newPasswordPane.isVisible());
        updatePasswordPane.setVisible(false);
        findPasswordPane.setVisible(false);
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
        findPasswordPane.setVisible(false);
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

    public void findPasswordButtonClicked(ActionEvent actionEvent) throws Exception {
        findPasswordPane.setVisible(!findPasswordPane.isVisible());
        updatePasswordPane.setVisible(false);
        newPasswordPane.setVisible(false);
        List<Passwords> passwordsList = PasswordsRepository.getPasswordsObservableList(user.getUsername());
        List<Passwords> newPasswordsList = new ArrayList<>();
        this.buttons = new Button[passwordsList.size()];
        int i = 0;

        for (Passwords passwords : passwordsList) {
            buttons[i] = new Button();
            buttons[i].setOnAction(this::handleButtonAction);
            newPasswordsList.add(new Passwords(passwords.getLoginName(), passwords.getPassword(), passwords.getUrl(), passwords.getMoreInformation(), buttons[i]));
            i++;
        }

        findPasswordTable.getItems().clear();
        findPasswordTable.getItems().addAll(newPasswordsList);
        this.passwordsList1 = newPasswordsList;
        passwordsList = null;
    }

    private void handleButtonAction(ActionEvent actionEvent) {
        try {
            int i = 0;
            for (Passwords passwords : passwordsList1) {
                if (actionEvent.getSource() == buttons[i]) {
                    JOptionPane.showMessageDialog(null, "Your password: " + EncryptDecryptPassword.decryptPassword(passwords.getPassword()));
                    break;
                }
                else {
                    i++;
                }
            }
        }
        catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }
    }

    public void copyPasswordButtonClicked(ActionEvent actionEvent) throws Exception {
        Passwords passwords = findPasswordTable.getSelectionModel().getSelectedItem();
        final ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(EncryptDecryptPassword.decryptPassword(passwords.getPassword()));
        Clipboard.getSystemClipboard().setContent(clipboardContent);
    }
}
