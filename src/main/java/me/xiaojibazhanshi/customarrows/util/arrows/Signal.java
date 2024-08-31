package me.xiaojibazhanshi.customarrows.util.arrows;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class Signal {

    private Signal() {}

    public static void detonateSilentFirework(Location location, FireworkEffect.Type type, Color color) {
        World world = location.getWorld();
        if (world == null) return;

        Firework firework = world.spawn(location, Firework.class);
        FireworkMeta meta = firework.getFireworkMeta();

        FireworkEffect effect = FireworkEffect.builder()
                .withColor(color)
                .with(type)
                .build();

        meta.addEffect(effect);
        meta.setPower(0);

        firework.setFireworkMeta(meta);
        firework.setSilent(true);

        firework.detonate();
    }

}
