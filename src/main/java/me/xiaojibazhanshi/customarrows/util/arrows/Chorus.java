package me.xiaojibazhanshi.customarrows.util.arrows;

import org.bukkit.Location;

import java.util.concurrent.ThreadLocalRandom;

public class Chorus {

    private Chorus() {}

    public static Location randomizeLocation(Location location, int maxOffset) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        double yClamp = 0.75;

        return new Location(
                location.getWorld(),
                location.getX() + random.nextDouble(-maxOffset, maxOffset),
                location.getY() + random.nextDouble(0, maxOffset * yClamp),
                location.getZ() + random.nextDouble(-maxOffset, maxOffset)
        );
    }

}
