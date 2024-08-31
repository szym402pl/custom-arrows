package me.xiaojibazhanshi.customarrows.util.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.util.GeneralUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nullable;

public class Ghost {

    private Ghost() {
    }

    public static void temporarilyConvertToDisplayItem(Block block, int delay, @Nullable Material replacement) {
        BlockData originalBlockData = block.getBlockData();
        Location blockLocation = block.getLocation();
        assert blockLocation.getWorld() != null;

        block.setType(Material.AIR);

        BlockDisplay blockDisplay = blockLocation.getWorld().spawn(blockLocation, BlockDisplay.class, (display) -> {
            display.setBlock(replacement != null ? replacement.createBlockData() : originalBlockData);
            display.setInvulnerable(true);
            display.setGravity(false);
        });

        new BukkitRunnable() {
            @Override
            public void run() {
                blockDisplay.remove();
                block.setBlockData(originalBlockData);
            }
        }.runTaskLater(CustomArrows.getInstance(), delay);
    }

    public static void shootFakeArrow(EntityShootBowEvent event, Player shooter) {
        Arrow arrow = shooter.getWorld().spawnArrow(event.getProjectile().getLocation(),
                event.getProjectile().getVelocity(), 3.0f, 0.0f, Arrow.class);

        arrow.setVelocity(event.getProjectile().getVelocity().multiply(1.04));
        arrow.setVisualFire(true);
        arrow.setVisibleByDefault(false);
        arrow.setGravity(false);
        arrow.setPersistent(true);

        GeneralUtil.removeArrowAfter(arrow, 300);
    }

    public static boolean isFakeArrow(Entity entity) {
        return entity instanceof Arrow arrow
                && (!arrow.isVisibleByDefault())
                && arrow.isVisualFire();
    }

}
