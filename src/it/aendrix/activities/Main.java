package it.aendrix.activities;

import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.HashMap;

public class Main extends JavaPlugin {

    private static Main instance;
    private static Database database;
    private static final HashMap<String, PlayerInstance> players = new HashMap<>();

    public void onEnable() {
        instance = this;

        try {
            database = new Database("localhost", 3306, "database", "password", "user");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public static Main getInstance() {
        return instance;
    }

    public static HashMap<String, PlayerInstance> getPlayers() {
        return players;
    }

    public static void addPlayer(String player) {
        players.put(player.toUpperCase(), new PlayerInstance(new HashMap<>()));
    }

    public static PlayerInstance getPlayer(String player) {
        return players.get(player.toUpperCase());
    }

    public static Database getDatabase() {
        return database;
    }
}