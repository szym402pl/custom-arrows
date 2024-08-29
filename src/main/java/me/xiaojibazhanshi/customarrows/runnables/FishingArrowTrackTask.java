package me.xiaojibazhanshi.customarrows.runnables;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;
import java.util.function.Consumer;

import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.color;
import static me.xiaojibazhanshi.customarrows.util.arrows.Fishing.*;
import static me.xiaojibazhanshi.customarrows.util.arrows.LavaWalker.applyLavaWalkerEffect;

public class FishingArrowTrackTask implements Consumer<BukkitTask> {

    private final Arrow arrow;
    private final Player shooter;

    public FishingArrowTrackTask(Arrow arrow, Player shooter) {
        this.arrow = arrow;
        this.shooter = shooter;
    }

    @Override
    public void accept(BukkitTask bukkitTask) {

        if (!shooter.isOnline()) {
            bukkitTask.cancel();
            return;
        }

        if (arrow.isDead() || arrow.isOnGround()) {
            shooter.sendTitle("", color("&7I can't fish there..."), 5, 25 ,5);

            bukkitTask.cancel();
            return;
        }

        if (!arrow.isInWater()) return;

        ItemStack randomItem = randomFishingLootTableItem();
        double offset = 0.1;

        Location arrowLocation = arrow.getLocation();
        World world = shooter.getWorld();

        shooter.playSound(arrowLocation, Sound.ENTITY_FISH_SWIM, 1.0F, 1.0F);
        world.spawnParticle(Particle.FISHING, arrowLocation, 2, offset, offset, offset);

        Bukkit.getScheduler().runTaskLater(CustomArrows.getInstance(), () -> {
            if (randomItem != null) {
                Item item = world.dropItem(arrowLocation, randomItem);

                pushItemTowardsPlayer(item, shooter);
            }

            notifyPlayer(randomItem, shooter);
        }, 40);

        arrow.remove();
        bukkitTask.cancel();
    }

}
