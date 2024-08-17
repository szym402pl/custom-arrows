package me.xiaojibazhanshi.customarrows.runnables;

import me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

import static me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil.randomizeLocation;

public class CrystalHealTask implements Consumer<BukkitTask> {

    private final Player target;
    private Map<UUID, EnderCrystal> crystalMap;
    private final EnderCrystal crystal;
    private final int durationInSeconds;
    private final int chosenPeriod;
    private final double maxPlayerHealth;

    private int counter = 1;

    public CrystalHealTask(Map<UUID, EnderCrystal> crystalMap, Player target, int durationInSeconds, int chosenPeriod) {
        this.target = target;
        this.crystalMap = crystalMap;
        this.crystal = crystalMap.get(target.getUniqueId());
        this.durationInSeconds = durationInSeconds;
        this.chosenPeriod = chosenPeriod;
        this.maxPlayerHealth = target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
    }

    @Override
    public void accept(BukkitTask bukkitTask) {
        if (counter * chosenPeriod == durationInSeconds * 20) bukkitTask.cancel();

        if (!target.isOnline() || target.isDead()) {
            crystalMap.remove(target.getUniqueId());
            crystal.remove();
        }

        Vector fromCrystalToTarget = ArrowSpecificUtil.getDirectionFromEntityToTarget(crystal, target)
                .multiply(0.02)
                .add(new Vector(0, 0.6, 0));

        crystal.setBeamTarget(target.getLocation().subtract(fromCrystalToTarget));

        double updatedHealth = Math.min(target.getHealth() + 0.4, maxPlayerHealth);
        target.setHealth(updatedHealth);

        counter++;
    }

}
