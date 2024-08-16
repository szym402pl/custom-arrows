package me.xiaojibazhanshi.customarrows.runnables;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;

public class StealthArrowRunnable extends BukkitRunnable {

    private int timer;
    private Player player;

    @Override
    public void run() {
        if (!player.isOnline()) {
            cancel();
            return;
        }

        AttributeInstance sneakingSpeedAttribute = player.getAttribute(Attribute.PLAYER_SNEAKING_SPEED);
        AttributeInstance movementSpeedAttribute = player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        assert movementSpeedAttribute != null;
        assert sneakingSpeedAttribute != null;

        if (timer <= 0) {
            sneakingSpeedAttribute.setBaseValue(sneakingSpeedAttribute.getDefaultValue());
            cancel();
            return;
        }

        if (player.isSneaking()) {
            sneakingSpeedAttribute.setBaseValue(movementSpeedAttribute.getDefaultValue() * 2.0);
        } else {
            sneakingSpeedAttribute.setBaseValue(sneakingSpeedAttribute.getDefaultValue());
        }

        timer--;
    }

    public void start(Player player, int runDurationInSeconds){
        this.player = player;
        timer = runDurationInSeconds * 4;

        runTaskTimer(CustomArrows.getInstance(), 2, 5);
    }
}

