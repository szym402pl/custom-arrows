package me.xiaojibazhanshi.customarrows.util.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.HashMap;
import java.util.Map;

public class Honeypot {

    private Honeypot() {}

    public static void placeTemporaryBlocks(Map<Location, Material> blockLocations, int deleteAfter, Material material) {
        if (blockLocations.isEmpty()) return;

        for (Location location : blockLocations.keySet()) {
            location.getBlock().setType(material);
        }

        Bukkit.getScheduler().runTaskLater(CustomArrows.getInstance(), () -> {
            for (Location location : blockLocations.keySet()) {
                Material originalMaterial = blockLocations.get(location);

                location.getBlock().setType(originalMaterial);
            }
        }, deleteAfter * 20L);
    }

    public static Map<Location, Material> getAHollowSphereAround(Location center, int radius) {
        Map<Location, Material> sphere = new HashMap<>();
        World world = center.getWorld();

        double pi = Math.PI;
        double doublePi = 2 * pi;

        for (double theta = 0; theta <= pi; theta += pi / (radius * 3)) {
            double sinTheta = Math.sin(theta);
            double cosTheta = Math.cos(theta);

            for (double phi = 0; phi <= doublePi; phi += pi / (radius * 3)) {
                double sinPhi = Math.sin(phi);
                double cosPhi = Math.cos(phi);

                int x = (int) Math.round(center.getX() + radius * sinTheta * cosPhi);
                int y = (int) Math.round(center.getY() + radius * cosTheta);
                int z = (int) Math.round(center.getZ() + radius * sinTheta * sinPhi);

                Location loc = new Location(world, x, y, z);

                Material material = loc.getBlock().getType();

                if (material == Material.AIR) {
                    sphere.put(loc, material);
                }
            }
        }

        return sphere;
    }

}
