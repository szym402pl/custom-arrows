package me.xiaojibazhanshi.customarrows.runnables;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.function.Consumer;

public class HelicopterTask implements Consumer<BukkitTask> {

    private final LivingEntity target;
    private final int durationInSeconds;
    private final float angle;
    private final int chosenPeriod;
    private final double heightIncrement;

    private int counter = 1;

    public HelicopterTask(LivingEntity target, int durationInSeconds, int chosenPeriod, float angle, double heightIncrement) {
        this.target = target;
        this.heightIncrement = heightIncrement;
        this.durationInSeconds = durationInSeconds;
        this.chosenPeriod = chosenPeriod;
        this.angle = angle;
    }

    @Override
    public void accept(BukkitTask bukkitTask) {
        int ticksInSecond = 20;
        double heightClamp = 3.0;

        if (counter * chosenPeriod >= durationInSeconds * ticksInSecond) {
            target.setGravity(true);
            bukkitTask.cancel();
        }

        if (target instanceof Player player) {
            Location newLocation = player.getLocation();

            newLocation.setYaw(newLocation.getYaw() + angle);
            newLocation.setY(newLocation.getY() + heightIncrement);

            player.teleport(newLocation);
        } else {
            double newHeight = heightIncrement / heightClamp;

            target.setRotation(target.getLocation().getYaw() + angle, target.getLocation().getPitch());
            target.setGravity(false);
            target.setVelocity(new Vector(0, newHeight, 0));
        }

        counter++;
    }

}
