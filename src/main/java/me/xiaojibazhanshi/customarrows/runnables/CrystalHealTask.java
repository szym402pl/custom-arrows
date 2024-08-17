package me.xiaojibazhanshi.customarrows.runnables;

import org.bukkit.Location;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.function.Consumer;

import static me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil.randomizeLocation;

public class CrystalHealTask implements Consumer<BukkitTask> {

    private final Player target;
    private final EnderCrystal crystal;
    private final int durationInSeconds;
    private final int chosenPeriod;

    private int counter = 1;

    public CrystalHealTask(Player target, EnderCrystal crystal, int durationInSeconds, int chosenPeriod) {
        this.target = target;
        this.crystal = crystal;
        this.durationInSeconds = durationInSeconds;
        this.chosenPeriod = chosenPeriod;
    }

    @Override
    public void accept(BukkitTask bukkitTask) {
        if (counter * chosenPeriod == durationInSeconds * 20) bukkitTask.cancel();

        crystal.setBeamTarget(target.getLocation());
        //target.setHealth(2);

        counter++;
    }

}
