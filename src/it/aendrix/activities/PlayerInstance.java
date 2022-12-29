package it.aendrix.activities;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.sql.SQLException;
import java.util.HashMap;

public class PlayerInstance {

    private String player;
    private HashMap<String, Integer> activity;
    private BukkitTask thread;

    public PlayerInstance(String player, HashMap<String, Integer> activity) {
        this.activity = activity;
        this.player = player;

        this.start();
    }

    public PlayerInstance(String player, HashMap<String, Integer> activity, boolean start) {
        this.activity = activity;
        this.player = player;

        if (!start)
            this.start();
    }

    public PlayerInstance(String player, SimplyDate date, Integer time, boolean start) {
        this.activity = new HashMap<>();
        this.activity.put(date.toString(),time);
        this.player = player;

        if (!start)
            this.start();
    }

    public void start() {
        this.thread = new BukkitRunnable() {
            @Override
            public void run() {
                String now = SimplyDate.getInstance().toString();

                if (activity.containsKey(now))
                    activity.replace(now, activity.get(now)+1);
                else
                    activity.put(now, 1);
            }
        }.runTaskTimer(Main.getInstance(), 1200L, 1200L);
    }

    public void stop() {
        if (this.thread != null)
            this.thread.cancel();
        this.thread = null;

        //UPDATE DB
        try {
            for (String date : activity.keySet())
                Main.getDatabase().setActivity(player, date, activity.get(date));
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }
}
