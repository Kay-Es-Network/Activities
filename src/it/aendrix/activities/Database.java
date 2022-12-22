package it.aendrix.activities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private String host, password, database, user;
    private int port;

    public Database(String host, int port, String database, String password, String user) throws SQLException, ClassNotFoundException {
        this.host = host;
        this.password = password;
        this.database = database;
        this.user = user;
        this.port = port;

        this.openConnection();
    }

    private Connection connection;

    public void openConnection() throws SQLException, ClassNotFoundException {
        if (connection != null && !connection.isClosed())
            return;
        synchronized (this) {
            if (connection != null && !connection.isClosed()) {
                System.out.println("CONNESSIONE A MYSQL FALLITA");
                return;
            }
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database,
                    this.user, this.password);
        }
    }

    // Date__,Name__,time(min)

}
