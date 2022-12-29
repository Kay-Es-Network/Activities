package it.aendrix.activities;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

public class Main extends JavaPlugin {

    private static Main instance;
    private static Database database;
    private static HashMap<String, PlayerInstance> players = new HashMap<>();

    public void onEnable() {
        instance = this;

        try {
            database();

            players = database.selectAllToday();

            for (Player p : Bukkit.getOnlinePlayers())
                if (players.containsKey(p.getName()))
                    players.get(p.getName()).start();
                else
                    addPlayer(p.getName());

            Main.getInstance().getServer().getPluginManager().registerEvents(new Listener(), this);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void onDisable() {
        for (PlayerInstance player : players.values())
            player.stop();
    }

    private void database() {
        new File("plugins/Activities").mkdir();

        File f = new File("plugins/Activities" + File.separator + "database.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
        if (!f.exists())
            try {
                f.createNewFile();

                cfg.set("database.address", "localhost");
                cfg.set("database.port", 3306);
                cfg.set("database.user", "Activities");
                cfg.set("database.password", "password");
                cfg.set("database.database", "Activities");
                cfg.set("database.table", "Activity");

                cfg.save(f);
            } catch (IOException ignored) {}
        else
            try {
                String address = cfg.getString("database.localhost"),
                db = cfg.getString("database.database"),
                password = cfg.getString("database.password"),
                user = cfg.getString("database.user"),
                table = cfg.getString("database.table");

                database = new Database(
                        address != null ? address : "localhost",
                        cfg.getInt("database.port"),
                        db != null ? db : "Activities",
                        password != null ? password : "password",
                        user != null ? user : "Activities",
                        table != null ? table : "Activity"
                );
            } catch (SQLException | ClassNotFoundException ignored) {
                ignored.printStackTrace();
            }
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