package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.copyArrow;
import static me.xiaojibazhanshi.customarrows.util.arrows.AimAssist.provideAimAssist;
import static me.xiaojibazhanshi.customarrows.util.arrows.Bouncing.executeBounce;
import static me.xiaojibazhanshi.customarrows.util.arrows.Homing.findEntityInSight;

public class BouncingArrow extends CustomArrow {

    private final List<UUID> bouncedAlready = new ArrayList<>();

    public BouncingArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&6Bouncing Arrow", "bouncing_arrow",
                                List.of("", "This arrow bounces off of blocks")),
                        Color.MAROON));
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        Block hitBlock = event.getHitBlock();
        if (hitBlock == null) return;

        Material type = hitBlock.getType();
        if (!type.isBlock() || !type.isSolid()) return;

        Arrow arrow = (Arrow) event.getEntity();
        if (arrow.isOnGround() || arrow.isInWater()) return;

        BlockFace hitBlockFace = event.getHitBlockFace();
        if (hitBlockFace == null) return;

        UUID arrowUUID = arrow.getUniqueId();
        if (hitBlockFace == BlockFace.UP && bouncedAlready.contains(arrowUUID)) return;

        event.setCancelled(true);

        executeBounce(arrow, hitBlockFace, bouncedAlready);
    }

}
