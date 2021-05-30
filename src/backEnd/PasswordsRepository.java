package backEnd;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Passwords;
import security.EncryptDecryptFile;
import security.EncryptDecryptPassword;

import javax.swing.*;
import java.io.*;
import java.util.Scanner;

public class PasswordsRepository {

    static String filePath = "UsersFiles\\";

    protected static ObservableList<Passwords> passwordsObservableList = FXCollections.observableArrayList();


    public void createNewPassword(String loginName, String password, String url, String moreInformation, String fileName) {

        try {
            FileWriter fileWriter = new FileWriter(filePath +  fileName, true);
            fileWriter.write("" + loginName + "," + EncryptDecryptPassword.encryptPassword(password) + "," +
                    url + "," + moreInformation);
            fileWriter.write(System.lineSeparator());
            JOptionPane.showMessageDialog(null, "New password successfully created!");
            fileWriter.close();
        }
        catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }
    }

    public static ObservableList<Passwords> getPasswordsObservableList(String fileName) throws Exception {
        passwordsObservableList.clear();
        Scanner scanner = new Scanner(new File(filePath + fileName + ".txt"))
                .useDelimiter(",|\\R");
        while (scanner.hasNext()) {
            String loginName = scanner.next();
            String password = scanner.next();
            String url = scanner.next();
            String moreInformation = scanner.next();

            Passwords passwords = new Passwords(loginName, password, url, moreInformation);
            passwordsObservableList.add(passwords);
        }

        for (Passwords passwords : passwordsObservableList) {
            System.out.println(passwords);
        }
        return passwordsObservableList;
    }

    public static void updatePassword(String fileName, String oldPassword, String newPassword) {
        File fileToBeModified = new File(filePath + fileName + ".txt");
        String oldContent = "";
        BufferedReader bufferedReader = null;
        FileWriter fileWriter = null;

        try {
            bufferedReader = new BufferedReader(new FileReader(fileToBeModified));

            String line = bufferedReader.readLine();
            while (line != null) {
                oldContent = oldContent + line + System.lineSeparator();
                line = bufferedReader.readLine();
            }

            String newContent = oldContent.replaceAll(oldPassword, newPassword);

            fileWriter = new FileWriter(fileToBeModified);
            fileWriter.write(newContent);
        }
        catch (IOException ioException) {
            ioException.printStackTrace();
        }
        finally {
            try {
                bufferedReader.close();
                fileWriter.close();
                JOptionPane.showMessageDialog(null, "Password changed successfully!");
            }
            catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
