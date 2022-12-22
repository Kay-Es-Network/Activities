package it.aendrix.activities;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

public class PlayerInstance {

    private HashMap<SimplyDate, Integer> activity;
    private BukkitTask thread;

    public PlayerInstance(HashMap<SimplyDate, Integer> activity) {
        this.activity = activity;

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

    }

    public HashMap<SimplyDate, Integer> getActivity() {
        return activity;
    }

    public void setActivity(HashMap<SimplyDate, Integer> activity) {
        this.activity = activity;
    }
}
