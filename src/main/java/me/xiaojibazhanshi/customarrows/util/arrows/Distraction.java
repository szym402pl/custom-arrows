package me.xiaojibazhanshi.customarrows.util.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.runnables.DistractionTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Distraction {

    private Distraction() {
    }

    public static void distract(int fireworkAmount, Location location) {
        int delay = 1;
        int period = 4;

        DistractionTask task = new DistractionTask(fireworkAmount, location);
        Bukkit.getScheduler().runTaskTimer(CustomArrows.getInstance(), task, delay, period);
    }

}
