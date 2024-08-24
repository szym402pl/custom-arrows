package me.xiaojibazhanshi.customarrows.util.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.runnables.MeteoriteStrikeTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Meteorite {

    private Meteorite() {

    }

    public static void executeOrderMeteorite(Location targetLocation) {
        adjustLocation(targetLocation);

        MeteoriteStrikeTask task = new MeteoriteStrikeTask(4, targetLocation, 4);
        Bukkit.getScheduler().runTaskTimer(CustomArrows.getInstance(), task, 0, 10);
    }

    public static void adjustLocation(Location location) {
        int adjustedY = 25;
        int adjustedZ = -34;

        location.setY(location.getY() + adjustedY);
        location.setZ(location.getZ() + adjustedZ);
    }

}
