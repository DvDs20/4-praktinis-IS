package backEnd;

import dataBase.ConstantForDB;
import dataBase.DataBaseConnection;
import models.User;
import org.springframework.security.crypto.bcrypt.BCrypt;
import security.HashPassword;

import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;
import java.io.Reader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {

    DataBaseConnection dataBaseConnection = new DataBaseConnection();
    private static User user = null;

    public void register(String username, String newPassword, String againNewPassword) throws Exception {

        String hashedPassword = HashPassword.hashPassword(newPassword);

        String sql = "SELECT * FROM " + ConstantForDB.USER_TABLE + " WHERE " + ConstantForDB.USER_USERNAME + " =?";
        PreparedStatement preparedStatement = dataBaseConnection.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();

        int number = 0;

        while (resultSet.next()) {
            number++;
        }
        resultSet.close();

        if (number > 0) {
            throw new Exception("User with this username is existing!");
        }

        if (!newPassword.equals(againNewPassword)) {
            throw new Exception("Passwords do not match!");
        }

        String sqlStringForRegistration = "INSERT INTO " + ConstantForDB.USER_TABLE + "(" +
                ConstantForDB.USER_USERNAME + "," + ConstantForDB.USER_PASSWORD + ")" +
                "VALUES (?,?)";
        try {
            PreparedStatement preparedStatement1 = dataBaseConnection.getConnection().prepareStatement(sqlStringForRegistration);
            preparedStatement1.setString(1, username);
            preparedStatement1.setString(2, hashedPassword);

            preparedStatement1.executeUpdate();
            preparedStatement1.close();
        }
        catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception);
        }
    }

    public User login (String username, String password) throws Exception {
        User user = null;

        String hashedPassword = HashPassword.hashPassword(password);

        String sql = "SELECT * FROM " + ConstantForDB.USER_TABLE + " WHERE " + ConstantForDB.USER_USERNAME + "=? AND " +
                ConstantForDB.USER_PASSWORD + "=?";
        PreparedStatement preparedStatement = dataBaseConnection.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, hashedPassword);

        ResultSet resultSet = preparedStatement.executeQuery();

        int number = 0;

        while (resultSet.next()) {
            number++;

            int userID = resultSet.getInt(ConstantForDB.USER_ID);
            String surname = resultSet.getString(ConstantForDB.USER_USERNAME);
            String password1 = resultSet.getString(ConstantForDB.USER_PASSWORD);

            user = new User(userID, surname, password1);
        }
        if (number == 0 || !hashedPassword.equals(user.getPassword())) {
            throw new Exception("Login incorrect");
        }
        resultSet.close();
        return user;
    }

    public void setUser (User user) {
        UserRepository.user = user;
    }
}
