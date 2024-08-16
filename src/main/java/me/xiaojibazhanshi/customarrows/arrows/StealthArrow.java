package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.runnables.StealthArrowRunnable;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil;
import me.xiaojibazhanshi.customarrows.util.GeneralUtil;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.Vector;

import java.util.List;

public class StealthArrow extends CustomArrow {

    public StealthArrow() {
        super(ArrowFactory.changeTippedColor // Or you can use #changeTippedEffect if you need the effect
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&0Stealth Arrow", "stealth_arrow",
                                List.of("", "This arrow is a good way", "to ambush your enemies","",
                                        "Note: The speed boost is", "applied only when sneaking!")),
                        Color.BLACK));
    }

    @Override
    public void onHitGround(ProjectileHitEvent event, Player shooter) {
        event.getEntity().remove();
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        if (!(event.getEntity() instanceof LivingEntity target)) return;

        Entity arrow = event.getDamager();
        arrow.setVelocity(arrow.getVelocity().multiply(1.25));

        Vector initialVelocity = target.getVelocity();

        Vector fromShooterToTarget = ArrowSpecificUtil.getDirectionFromEntityToTarget(shooter, target);
        Vector modifiedFSTT = fromShooterToTarget.setX(0).setZ(0);

        target.setVelocity(initialVelocity.add(modifiedFSTT));
    }

    @Override
    public void onShoot(EntityShootBowEvent event, Player shooter) {
        shooter.setSilent(true);
        shooter.setSneaking(true);

        StealthArrowRunnable runnable = new StealthArrowRunnable();
        runnable.start(shooter, 4);
    }

    @Override
    public void onPlayerLeave(PlayerQuitEvent event, Player player) {
        player.setSilent(false);

        AttributeInstance sneakingSpeedAttribute = player.getAttribute(Attribute.PLAYER_SNEAKING_SPEED);
        assert sneakingSpeedAttribute != null;

        sneakingSpeedAttribute.setBaseValue(sneakingSpeedAttribute.getDefaultValue());
    }
}
