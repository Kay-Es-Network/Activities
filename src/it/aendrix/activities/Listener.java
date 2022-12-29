package it.aendrix.activities;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Listener implements org.bukkit.event.Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if (!p.hasPermission("countactivities") && !p.hasPermission("*"))
            return;

        PlayerInstance instance = Main.getPlayer(p.getName().toUpperCase());

        if (instance != null)
            instance.start();
        else
            Main.addPlayer(p.getName());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        if (!p.hasPermission("countactivities") && !p.hasPermission("*"))
            return;

        PlayerInstance instance = Main.getPlayer(p.getName().toUpperCase());

        if (instance != null)
            instance.stop();
    }


}
