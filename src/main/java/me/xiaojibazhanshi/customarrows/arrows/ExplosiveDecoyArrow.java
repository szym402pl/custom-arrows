package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.List;
import java.util.Optional;

import static me.xiaojibazhanshi.customarrows.util.arrows.ExplosiveDecoy.spawnAngryDecoy;

public class ExplosiveDecoyArrow extends CustomArrow {

    public ExplosiveDecoyArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&eExplosive Decoy Arrow", "explosive_decoy_arrow",
                                List.of("", "This arrow will spawn a decoy", "that explodes upon touching it")),
                        Color.YELLOW));
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        Arrow arrow = (Arrow) event.getEntity();
        int radius = 4;

        Optional<Entity> firstNearbyEntity = arrow.getNearbyEntities(radius, radius, radius)
                .stream()
                .filter(entity -> entity instanceof LivingEntity)
                .findFirst();

        firstNearbyEntity.ifPresent(entity -> spawnAngryDecoy((LivingEntity) entity, arrow.getLocation()));

        arrow.remove();
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        // Zombie hit check executed in the Listener itself
        Entity entity = event.getEntity();

        if (!(entity instanceof LivingEntity livingEntity)) return;

        spawnAngryDecoy(livingEntity, null);
    }


}
