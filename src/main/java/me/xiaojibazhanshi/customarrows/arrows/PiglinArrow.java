package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Map;

import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.color;
import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.removeEntityAfter;
import static me.xiaojibazhanshi.customarrows.util.arrows.Honeypot.getAHollowSphereAround;
import static me.xiaojibazhanshi.customarrows.util.arrows.Honeypot.placeTemporaryBlocks;
import static me.xiaojibazhanshi.customarrows.util.arrows.Piglin.arePiglinsNearby;
import static me.xiaojibazhanshi.customarrows.util.arrows.Repulsion.detonateFirework;

public class PiglinArrow extends CustomArrow {

    public PiglinArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&7Piglin Arrow", "piglin_arrow",
                                List.of("", "This arrow will attract nearby", "piglins to the target location")),
                        Color.GRAY));
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        Block hitBlock = event.getHitBlock();
        if (hitBlock == null) return;

        Block blockAbove = hitBlock.getRelative(BlockFace.UP);
        if (blockAbove.getType() == Material.LAVA) return;

        Arrow arrow = (Arrow) event.getEntity();
        Location targetLocation = arrow.getLocation();
        World world = targetLocation.getWorld();
        assert world != null;

        if (world.getEnvironment() != World.Environment.NETHER) {
            shooter.sendTitle("", color("&7This is not &cThe Nether&7..."), 5, 25, 5);
            return;
        }

        if (!arePiglinsNearby(arrow)) {
            shooter.sendTitle("", color("&7There are no &cPiglins &7nearby..."), 5, 25, 5);
            return;
        }

        arrow.remove();

        ItemStack nuggetItem = new ItemStack(Material.GOLD_INGOT);
        Item nugget = world.dropItem(targetLocation, nuggetItem);

        nugget.setPickupDelay(999);
        nugget.setVisibleByDefault(false);

        removeEntityAfter(nugget, 15 * 20);
        detonateFirework(targetLocation, FireworkEffect.Type.BALL, Color.YELLOW);
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        Entity entity = event.getEntity();
        if (entity.isDead() || !(entity instanceof LivingEntity)) return;

        Arrow arrow = (Arrow) event.getDamager();
        Location targetLocation = arrow.getLocation();
        World world = targetLocation.getWorld();
        assert world != null;

        if (world.getEnvironment() != World.Environment.NETHER) {
            shooter.sendTitle("", color("&7This is not &cThe Nether&7..."), 5, 25, 5);
            return;
        }

        if (!arePiglinsNearby(arrow)) {
            shooter.sendTitle("", color("&7There are no &cPiglins &7nearby..."), 5, 25, 5);
            return;
        }

        arrow.remove();

        ItemStack nuggetItem = new ItemStack(Material.GOLD_INGOT);
        Item nugget = world.dropItem(targetLocation, nuggetItem);

        nugget.setPickupDelay(999);
        nugget.setVisibleByDefault(false);

        removeEntityAfter(nugget, 15 * 20);
        detonateFirework(targetLocation, FireworkEffect.Type.BALL, Color.YELLOW);
    }


}
