package me.xiaojibazhanshi.customarrows.util.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.runnables.LightningStrikeTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.concurrent.ThreadLocalRandom;

public class Thunder {

    private Thunder() {
    }

    public static Location randomizeLocation(Location location, int maxOffset) {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        return new Location(
                location.getWorld(),
                location.getX() + random.nextDouble(-maxOffset, maxOffset),
                location.getY() + random.nextDouble(-maxOffset, maxOffset),
                location.getZ() + random.nextDouble(-maxOffset, maxOffset)
        );
    }

    public static void createThunderStrike(Location location, int thunderAmount, int maxOffset, long strikePeriod) {
        LightningStrikeTask task = new LightningStrikeTask(thunderAmount, location, maxOffset);

        Bukkit.getScheduler().runTaskTimer(CustomArrows.getInstance(), task, 0, strikePeriod);
    }

}
