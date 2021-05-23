package backEnd;

import dataBase.ConstantForDB;
import dataBase.DataBaseConnection;
import models.User;
import org.springframework.security.crypto.bcrypt.BCrypt;
import security.HashPassword;

import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.io.Reader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {

    DataBaseConnection dataBaseConnection = new DataBaseConnection();
    private static User user = null;

    public void register(String username, String newPassword, String againNewPassword) throws Exception {

        String hashedNewPassword = HashPassword.BCryptPassword(newPassword);
        byte[] passwordByte = hashedNewPassword.getBytes();
        String hashedAgainNewPassword = HashPassword.BCryptPassword(againNewPassword);


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
            preparedStatement1.setBinaryStream(2, new ByteArrayInputStream(passwordByte), passwordByte.length);

            preparedStatement1.executeUpdate();
            preparedStatement1.close();
        }
        catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception);
        }
    }

    public  void login (String username, String password) throws SQLException, ClassNotFoundException {
        User user = null;

        String hashedPassword = HashPassword.BCryptPassword(password);
        byte[] passwordByte = hashedPassword.getBytes();

        String sql = "SELECT * FROM " + ConstantForDB.USER_TABLE + " WHERE " + ConstantForDB.USER_USERNAME + "=? AND " +
                ConstantForDB.USER_PASSWORD + "=?";
        PreparedStatement preparedStatement = dataBaseConnection.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, username);
        preparedStatement.setBinaryStream(2, new ByteArrayInputStream(passwordByte), passwordByte.length);

        ResultSet resultSet = preparedStatement.executeQuery();

        int number = 0;

        while (resultSet.next()) {
            number++;

            int userID = resultSet.getInt(ConstantForDB.USER_ID);
            String surname = resultSet.getString(ConstantForDB.USER_USERNAME);
            byte[] passwordByte1 = resultSet.getBytes(ConstantForDB.USER_PASSWORD);
        }
    }
}
