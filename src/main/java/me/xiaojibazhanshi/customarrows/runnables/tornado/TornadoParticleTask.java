package me.xiaojibazhanshi.customarrows.runnables.tornado;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.function.Consumer;

import static me.xiaojibazhanshi.customarrows.util.arrows.BlackHole.generateOneHighRing;

public class TornadoParticleTask implements Consumer<BukkitTask> {

    private final Location location;
    private final int durationInSeconds;
    private final int chosenPeriod;

    private int counter = 1;

    public TornadoParticleTask(Location location, int durationInSeconds, int chosenPeriod) {
        this.durationInSeconds = durationInSeconds;
        this.chosenPeriod = chosenPeriod;
        this.location = location;
    }

    @Override
    public void accept(BukkitTask bukkitTask) {
        int ticksInSecond = 20;
        if (counter * chosenPeriod >= durationInSeconds * ticksInSecond) bukkitTask.cancel();

        generateTornado(location.getWorld(), 20, 0.4);

        counter++;
    }

    private void generateTornado(World world, int height, double step) {

        for (double i = 0; i < height; i += step) { // iterate through tornado height
            double radius = i * 0.5;

            double densityMultiplier = 2.0;
            double density = radius * densityMultiplier;

            Location currentLocation = location.clone().add(new Vector(0, i, 0));
            List<Location> ringLocations = generateOneHighRing(currentLocation, radius, density);

            for (Location particleLocation : ringLocations) { // spawning particles themselves
                world.spawnParticle(Particle.DUST,
                        particleLocation,
                        1,
                        0.0, 0.0, 0.0,
                        0.0,
                        new Particle.DustOptions(Color.GRAY, (float) radius), // it's on i to ensure the particles get bigger
                        true);
            }
        }

    }

}
