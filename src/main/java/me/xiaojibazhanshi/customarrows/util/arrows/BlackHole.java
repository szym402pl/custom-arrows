package me.xiaojibazhanshi.customarrows.util.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.runnables.BlackHoleAnimationTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlackHole {

    public static List<Location> generateSphere(Location center, double radius, double pointDensity) {
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

    public static List<Location> generateOneHighRing(Location center, double radius, double pointDensity) {
        List<Location> points = new ArrayList<>();

        double step = 2 * Math.PI / (pointDensity * 2 * Math.PI * radius);

        for (double angle = 0.0; angle < 2 * Math.PI; angle += step) {
            double x = radius * Math.cos(angle);
            double z = radius * Math.sin(angle);

            Location point = center.clone().add(x, 0, z);
            points.add(point);
        }

        return points;
    }

    public static Map<Location, BlockData> getBlocksAround(Location center, double radius) {
        Map<Location, BlockData> blockMap = new HashMap<>();

        World world = center.getWorld();
        int centerX = center.getBlockX();
        int centerY = center.getBlockY();
        int centerZ = center.getBlockZ();

        for (double x = centerX - radius; x <= centerX + radius; x++) {
            for (double y = centerY - radius; y <= centerY + radius; y++) {
                for (double z = centerZ - radius; z <= centerZ + radius; z++) {
                    assert world != null;
                    Block block = world.getBlockAt((int) x, (int) y, (int) z);

                    if (block.getType() != Material.AIR && block.getType() != Material.BEDROCK) {
                        blockMap.put(block.getLocation(), block.getBlockData());

                        block.setType(Material.AIR);
                    }
                }
            }
        }

        return blockMap;
    }

    public static void executeBlackHoleAnimation(Location location, double radius) {
        Map<Location, BlockData> blockDataMap = getBlocksAround(location, radius);
        List<BlockDisplay> blockDisplayList = recreateBrokenBlocks(blockDataMap);

        BlackHoleAnimationTask task = new BlackHoleAnimationTask(location, blockDisplayList);
        Bukkit.getScheduler().runTaskTimer(CustomArrows.getInstance(), task, 1, 1);
    }

    public static void animateTowardBlackHole(Location blackHole, BlockDisplay blockDisplay) {
        Location currentLocation = blockDisplay.getLocation();
        Vector direction = blackHole.toVector().subtract(currentLocation.toVector()).normalize();

        currentLocation.add(direction);

        double yaw = Math.toDegrees(Math.atan2(direction.getZ(), direction.getX())) - 90;
        double pitch = Math.toDegrees(Math.asin(-direction.getY()));

        currentLocation.setYaw((float) yaw);
        currentLocation.setPitch((float) pitch);

        blockDisplay.setTeleportDuration(20);
        new BukkitRunnable() {
            @Override
            public void run() {
                blockDisplay.teleport(blackHole);
            }
        }.runTaskLater(CustomArrows.getInstance(), 2);

        new BukkitRunnable() {
            @Override
            public void run() {
                blockDisplay.remove();
            }
        }.runTaskLater(CustomArrows.getInstance(), 20);
    }

    public static List<BlockDisplay> recreateBrokenBlocks(Map<Location, BlockData> blockDataMap) {
        List<BlockDisplay> recreatedBlocks = new ArrayList<>();

        for (Location blockLocation : blockDataMap.keySet()) {
            assert blockLocation.getWorld() != null;

            BlockDisplay blockDisplay = blockLocation.getWorld().spawn(blockLocation, BlockDisplay.class, (display) -> {
                display.setBlock(blockDataMap.get(blockLocation));
                display.setInvulnerable(true);
                display.setGravity(false);
            });

            recreatedBlocks.add(blockDisplay);
        }

        return recreatedBlocks;
    }

}
