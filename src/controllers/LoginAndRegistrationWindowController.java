package controllers;

import backEnd.UserRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.User;
import security.EncryptDecryptFile;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LoginAndRegistrationWindowController {

    @FXML
    private Pane loginPane;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signInButton;

    @FXML
    private Button signUpButton;

    @FXML
    private Pane registrationPane;

    @FXML
    private TextField usernameForRegistrationTextField;

    @FXML
    private PasswordField passwordForRegistrationField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button backToSignInPaneButton;

    @FXML
    private Button confirmRegistrationButton;

    UserRepository userRepository = new UserRepository();


    public void signInButtonClicked(ActionEvent actionEvent) throws IOException {
        signInButton.getScene().getWindow().hide();
        try {
            User user = userRepository.login(usernameTextField.getText(), passwordField.getText());
            if (user != null) {
                userRepository.setUser(user);
                EncryptDecryptFile.decryptFile("C:\\Users\\deivi\\Documents\\Informacijos saugumas\\4 praktinis darbas\\UsersFiles\\" + user.getUsername() + ".txt");
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../frontEnd/mainWindow.fxml"));
                Parent parent = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle(user.getUsername());
                stage.setScene(new Scene(parent));
                stage.setResizable(false);
                stage.show();
            }
            else {
                throw new Exception("user = null");
            }
        }
        catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
            Parent parent = FXMLLoader.load(getClass().getResource("../frontEnd/login&RegistrationWindow.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Sign in | Sign up");
            stage.setScene(new Scene(parent));
            stage.show();
        }
    }

    public void signUpButtonClicked(ActionEvent actionEvent) {
        loginPane.setVisible(!loginPane.isVisible());
        registrationPane.setVisible(!registrationPane.isVisible());
    }

    public void backToSignInPaneButtonClicked(ActionEvent actionEvent) {
        registrationPane.setVisible(!registrationPane.isVisible());
        loginPane.setVisible(!loginPane.isVisible());
    }

    public void confirmRegistrationButtonClicked(ActionEvent actionEvent) {
        try {
            userRepository.register(usernameForRegistrationTextField.getText(), passwordForRegistrationField.getText(), confirmPasswordField.getText());
            Path path = Paths.get("C:\\Users\\deivi\\Documents\\Informacijos saugumas\\4 praktinis darbas\\UsersFiles\\" + usernameForRegistrationTextField.getText() + ".txt");
            try {
                Path filePath = Files.createFile(path);
                System.out.println("File created at path: " + filePath);
            }
            catch (IOException ioException) {
                ioException.printStackTrace();
            }
            EncryptDecryptFile.encryptFile(String.valueOf(path));

            JOptionPane.showMessageDialog(null, "Registration successful!");
            registrationPane.setVisible(!registrationPane.isVisible());
            loginPane.setVisible(!loginPane.isVisible());
        }
        catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }

    }
}
