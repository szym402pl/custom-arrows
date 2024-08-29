package me.xiaojibazhanshi.customarrows.util.arrows;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Rainbow {

    private Rainbow() {

    }

    public static List<Location> filterLocationsToAbove(List<Location> originalLocations, Location center) {
        return originalLocations
                .stream()
                .filter(location -> location.getY() > center.getY())
                .toList();
    }

    public static List<Location> generateVerticalRing(Location center, double radius, int segments, Player player) {
        List<Location> locations = new ArrayList<>();
        World world = center.getWorld();

        if (world == null) {
            throw new IllegalArgumentException("Center location's world cannot be null");
        }

        double angleStep = 2 * Math.PI / segments;

        float yaw = player.getLocation().getYaw();
        boolean isFacingNorthSouth = (yaw >= -45 && yaw < 45) || (yaw >= 135 || yaw < -135);

        for (int i = 0; i < segments; i++) {
            double angle = i * angleStep;
            double x, z;

            if (isFacingNorthSouth) {
                x = center.getX() + radius * Math.cos(angle);
                z = center.getZ();
            } else {
                x = center.getX();
                z = center.getZ() + radius * Math.cos(angle);
            }

            double y = center.getY() + radius * Math.sin(angle);

            Location location = new Location(world, x, y, z);
            locations.add(location);
        }

        return locations;
    }


}
