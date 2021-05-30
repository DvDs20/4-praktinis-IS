package models;

import javafx.scene.control.Button;

public class Passwords {
    private String loginName;
    private String password;
    private String url;
    private String moreInformation;
    private Button button;

    public Passwords(String loginName, String password, String url, String moreInformation) {
        this.loginName = loginName;
        this.password = password;
        this.url = url;
        this.moreInformation = moreInformation;
    }

    public Passwords(String loginName, String password, String url, String moreInformation, Button button) {
        this.loginName = loginName;
        this.password = password;
        this.url = url;
        this.moreInformation = moreInformation;
        this.button = button;
        button.setText("Show password");
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMoreInformation() {
        return moreInformation;
    }

    public void setMoreInformation(String moreInformation) {
        this.moreInformation = moreInformation;
    }
}
