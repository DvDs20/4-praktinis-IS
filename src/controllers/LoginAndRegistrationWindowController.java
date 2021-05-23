package controllers;

import backEnd.UserRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import models.User;

import javax.swing.*;

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


    public void signInButtonClicked(ActionEvent actionEvent) {

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
        UserRepository userRepository = new UserRepository();
        try {
            userRepository.register(usernameForRegistrationTextField.getText(), passwordForRegistrationField.getText(), confirmPasswordField.getText());
            JOptionPane.showMessageDialog(null, "Registration successful!");
            registrationPane.setVisible(!registrationPane.isVisible());
            loginPane.setVisible(!loginPane.isVisible());
        }
        catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }

    }
}
