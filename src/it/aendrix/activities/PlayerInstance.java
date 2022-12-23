package it.aendrix.activities;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.sql.SQLException;
import java.util.HashMap;

public class PlayerInstance {

    private String player;
    private HashMap<SimplyDate, Integer> activity;
    private BukkitTask thread;

    public PlayerInstance(String player, HashMap<SimplyDate, Integer> activity) {
        this.activity = activity;
        this.player = player;

        this.start();
    }

    public PlayerInstance(String player, HashMap<SimplyDate, Integer> activity, boolean start) {
        this.activity = activity;
        this.player = player;

        if (!start)
            this.start();
    }

    public PlayerInstance(String player, SimplyDate date, Integer time, boolean start) {
        this.activity = new HashMap<>();
        this.activity.put(date,time);
        this.player = player;

        if (!start)
            this.start();
    }

    public void start() {
        this.thread = new BukkitRunnable() {
            @Override
            public void run() {
                SimplyDate now = SimplyDate.getInstance();

                if (activity.containsKey(now))
                    activity.replace(now, activity.get(now)+1);
                else
                    activity.put(now, 1);
            }
        }.runTaskTimerAsynchronously(Main.getInstance(), 1200L, 1200L);
    }

    public void stop() {
        this.thread.cancel();
        this.thread = null;

        //UPDATE DB
        try {
            for (SimplyDate date : activity.keySet())
                Main.getDatabase().setActivity(player, date.toString(), activity.get(date));
        } catch (SQLException ignored) {}
    }

    public HashMap<SimplyDate, Integer> getActivity() {
        return activity;
    }

    public void setActivity(HashMap<SimplyDate, Integer> activity) {
        this.activity = activity;
    }
}
