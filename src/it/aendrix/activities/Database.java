package it.aendrix.activities;

import java.sql.*;
import java.util.HashMap;

public class Database {

    private String host, password, database, user;
    private int port;

    private String table;

    private PreparedStatement selectCond;
    private PreparedStatement updateSet;
    private PreparedStatement insert;

    private Connection connection;

    public Database(String host, int port, String database, String password, String user, String table) throws SQLException, ClassNotFoundException {
        this.host = host;
        this.password = password;
        this.database = database;
        this.user = user;
        this.port = port;
        this.table = table;

        this.openConnection();

        this.selectCond = this.connection.prepareStatement("SELECT * FROM "+this.table+" WHERE Name = ? AND Data = ?");
        this.updateSet = this.connection.prepareStatement("UPDATE "+this.table+" SET Time = ? WHERE Name = ? AND Data = ?");
        this.insert = this.connection.prepareStatement("INSERT INTO "+this.table+" ('Name','Data',Time) VALUES ('?','?',?)");
    }

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

    // DATA__,Name__,time(min)
    public ResultSet select(String name, String date) throws SQLException {
        this.selectCond.setString(1, name);
        this.selectCond.setString(2, date);

        return this.selectCond.executeQuery();
    }

    public void update(String name, String date, int time) throws SQLException {
        this.updateSet.setInt(1, time);
        this.updateSet.setString(2, name);
        this.updateSet.setString(3, date);

        this.updateSet.executeUpdate();
    }

    public void insert(String name, String date, int time) throws SQLException {
        this.insert.setString(1, name);
        this.insert.setString(2, date);
        this.insert.setInt(3, time);

        this.insert.executeUpdate();
    }

    public boolean existsActivity(String name, String date) throws SQLException {
        return select(name, date).next();
    }

    public void setActivity(String name, String date, int time) throws SQLException {
        if (existsActivity(name,date))
            update(name, date, time);
        else
            insert(name, date, time);
    }

    public HashMap<String , PlayerInstance> selectAllToday() throws SQLException {
        HashMap<String , PlayerInstance> data = new HashMap<>();

        Statement sql = this.connection.createStatement();
        ResultSet set = sql.executeQuery("SELECT DISTINCT * FROM "+this.table+" WHERE Data = "+SimplyDate.getInstance());

        while (set.next())
            data.put(set.getString("Name"), new PlayerInstance(set.getString("Name"), new SimplyDate(set.getString("Data")), set.getInt("Time"), false));

        sql.close();
        return data;
    }
}
