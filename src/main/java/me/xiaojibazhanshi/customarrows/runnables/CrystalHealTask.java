package me.xiaojibazhanshi.customarrows.runnables;

import me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

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
        final int ticksInSecond = 20;
        final double healingFactor = 0.4;
        final double directionClamp = 0.02;
        final Vector neededYSubtraction = new Vector(0, 0.6, 0);

        if (counter * chosenPeriod >= durationInSeconds * ticksInSecond) bukkitTask.cancel();

        if (!target.isOnline() || target.isDead()) {
            crystalMap.remove(target.getUniqueId());
            crystal.remove();
        }

        Vector fromCrystalToTarget = ArrowSpecificUtil.getDirectionFromEntityToTarget(crystal, target)
                .multiply(directionClamp)
                .add(neededYSubtraction);

        crystal.setBeamTarget(target.getLocation().subtract(fromCrystalToTarget));

        double updatedHealth = Math.min(target.getHealth() + healingFactor, maxPlayerHealth);
        target.setHealth(updatedHealth);

        counter++;
    }

}
