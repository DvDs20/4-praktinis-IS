package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection extends DataBaseConfig{

    Connection connection;

    public Connection getConnection() throws ClassNotFoundException, SQLException {

        String connectionString = "jdbc:mysql://" + dataBaseHost + ":" + dataBasePort + "/" + dataBaseName;

        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(connectionString, dataBaseUser, dataBasePassword);

        return connection;
    }
}
