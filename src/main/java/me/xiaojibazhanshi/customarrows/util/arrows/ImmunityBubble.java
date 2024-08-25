package me.xiaojibazhanshi.customarrows.util.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import org.bukkit.*;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ImmunityBubble {

    private ImmunityBubble() {

    }

    public static List<Location> generateSpherePoints(Location center, double radius, double pointDensity) {
        List<Location> points = new ArrayList<>();

        double step = 1.0 / pointDensity;

        for (double phi = 0.0; phi < Math.PI * 2; phi += step) {
            for (double theta = 0.0; theta < Math.PI; theta += step) {
                double x = radius * Math.cos(phi) * Math.sin(theta);
                double y = radius * Math.sin(phi) * Math.sin(theta);
                double z = radius * Math.cos(theta);

                Location point = center.clone().add(x, y, z);
                points.add(point);
            }
        }

        return points;
    }

    public static void runBubbleTask(Arrow arrow, List<Location> points, Set<UUID> invincibles, int durationInSeconds, int radius) {
        BukkitTask task = Bukkit.getScheduler().runTaskTimer(CustomArrows.getInstance(), () -> {
            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.BLUE, 0.5F);
            World world = points.getFirst().getWorld();

            for (Location point : points) {
                world.spawnParticle(Particle.DUST,
                        point,
                        1,
                        0.0, 0.0, 0.0,
                        0.0,
                        dustOptions,
                        true);
            }

            arrow.getNearbyEntities(radius, radius, radius)
                    .stream()
                    .filter(entity -> entity instanceof LivingEntity)
                    .forEach(entity -> {
                        invincibles.add(entity.getUniqueId());
                    });

        }, 1, 4);

        Bukkit.getScheduler().runTaskLater(CustomArrows.getInstance(), () -> {
            task.cancel();
            arrow.remove();
            invincibles.clear();
        }, durationInSeconds * 20L);
    }

}
