package me.xiaojibazhanshi.customarrows.util.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Split {

    private Split() {
    }

    public static void initiateFourWayArrowsOn(LivingEntity target) {
        Vector[] directions = {
                new Vector(0, 0, -4.5),
                new Vector(0, 0, 4.5),
                new Vector(4.5, 0, 0),
                new Vector(-4.5, 0, 0)
        };

        Arrow[] arrows = new Arrow[4];
        Location targetLocation = target.getEyeLocation().clone();

        for (int i = 0; i < directions.length; i++) {
            Location arrowLocation = targetLocation.clone().add(directions[i]);

            arrows[i] = spawnNoGravityArrow(arrowLocation);
            animateSplitArrow(arrows[i], arrowLocation, targetLocation);
        }

        for (int i = 0; i < arrows.length; i++) {
            final int index = i;

            new BukkitRunnable() {
                @Override
                public void run() {
                    directFWArrowTowardsEntity(arrows[index], target);
                }
            }.runTaskLater(CustomArrows.getInstance(), 15L * (i + 1));

            GeneralUtil.removeArrowAfter(arrows[index], 30L * (i + 1));
        }
    }

    private static void directFWArrowTowardsEntity(Arrow arrow, LivingEntity target) {
        if (target == null || target.isDead()) {
            arrow.remove();
            return;
        }

        Location targetLocation = target.getEyeLocation();
        Vector directionToPlayer = targetLocation.toVector().subtract(arrow.getLocation().toVector()).normalize();

        arrow.setVelocity(directionToPlayer.multiply(2));
    }

    private static Arrow spawnNoGravityArrow(Location location) {
        assert location.getWorld() != null;
        Arrow arrow = location.getWorld().spawn(location, Arrow.class);

        arrow.setGravity(false);
        arrow.setCritical(false);
        arrow.setGlowing(true);

        return arrow;
    }

    private static void animateSplitArrow(Arrow arrow, Location startingLocation, Location targetLocation) {
        Vector directionToTarget = targetLocation.toVector().subtract(startingLocation.toVector()).normalize();

        arrow.setVelocity(new Vector(0, 0.3, 0));

        Bukkit.getScheduler().runTaskLater(CustomArrows.getInstance(), () ->
                arrow.setVelocity(directionToTarget.multiply(0.05)), 10);
    }

}
