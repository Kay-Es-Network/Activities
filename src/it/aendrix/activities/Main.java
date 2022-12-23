package it.aendrix.activities;

import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.HashMap;

public class Main extends JavaPlugin {

    private static Main instance;
    private static Database database;
    private static HashMap<String, PlayerInstance> players = new HashMap<>();

    public void onEnable() {
        instance = this;

        try {
            database = new Database("localhost", 3306, "Activities", "password", "user", "activity");

            this.players = this.database.selectAllToday();

            Main.getInstance().getServer().getPluginManager().registerEvents(new Listener(), this);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void onDisable() {
        for (PlayerInstance player : players.values())
            player.stop();
    }

    public static Main getInstance() {
        return instance;
    }

    public static HashMap<String, PlayerInstance> getPlayers() {
        return players;
    }

    public static void addPlayer(String player) {
        players.put(player.toUpperCase(), new PlayerInstance(player, new HashMap<>()));
    }

    public static PlayerInstance getPlayer(String player) {
        return players.get(player.toUpperCase());
    }

    public static Database getDatabase() {
        return database;
    }

}