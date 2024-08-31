package me.xiaojibazhanshi.customarrows.util.arrows;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.util.Vector;

import static me.xiaojibazhanshi.customarrows.util.arrows.Homing.getDirectionFromTo;

public class Laser {

    private Laser() {
    }

    public static void createParticleLaser(Location startingLocation, Location endLocation, Color color) {
        Vector direction = getDirectionFromTo(startingLocation, endLocation);
        double distance = startingLocation.distance(endLocation);

        Location newParticleLocation = startingLocation.clone();
        World world = newParticleLocation.getWorld();

        assert world != null;
        double step = 0.25;

        for (double i = 0; i < distance; i += step) {
            newParticleLocation.add(direction.clone().multiply(step));
            world.spawnParticle(Particle.DUST, newParticleLocation, 1, new Particle.DustOptions(color, 1.0F));
        }
    }

    public static void createParticleLaser(Location startingLocation, int length, Color color) {
        Location newParticleLocation = startingLocation.clone();
        World world = newParticleLocation.getWorld();
        Vector direction = newParticleLocation.getDirection().normalize();

        assert world != null;
        double step = 0.2;

        for (double i = 0; i < length; i += step) {
            newParticleLocation.add(direction.clone().multiply(step));
            world.spawnParticle(Particle.DUST, newParticleLocation, 1, new Particle.DustOptions(color, 1.0F));
        }
    }

}
