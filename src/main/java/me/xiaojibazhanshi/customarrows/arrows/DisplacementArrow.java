package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import me.xiaojibazhanshi.customarrows.util.GeneralUtil;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.Vector;

import java.util.List;

import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.color;

public class DisplacementArrow extends CustomArrow {

    public DisplacementArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&fDisplacement Arrow", "displacement_arrow",
                                List.of("", "This arrow will displace", "any block that you hit")),
                        Color.WHITE));
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        Block hitBlock = event.getHitBlock();
        if (hitBlock == null) return;

        Material type = hitBlock.getType();
        Location location = hitBlock.getLocation();

        if (!type.isBlock() || !type.isSolid()) {
            shooter.sendTitle("", color("&7This isn't movable..."), 5, 25 ,5);
            return;
        }

        hitBlock.setType(Material.AIR);

        Arrow arrow = (Arrow) event.getEntity();
        Vector velocity = arrow.getVelocity();

        if (velocity.getY() < 0)
            velocity.setY(0.1);

        World world = shooter.getWorld();
        FallingBlock newBlock = world.spawnFallingBlock(location, type.createBlockData());

        newBlock.setHurtEntities(true);
        newBlock.setInvulnerable(true);

        arrow.remove();
        newBlock.setVelocity(velocity);
    }
}
