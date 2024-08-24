package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.color;
import static me.xiaojibazhanshi.customarrows.util.arrows.AimAssist.provideAimAssist;
import static me.xiaojibazhanshi.customarrows.util.arrows.Dimension.teleportToOneOfDimensions;
import static me.xiaojibazhanshi.customarrows.util.arrows.Homing.findEntityInSight;
import static me.xiaojibazhanshi.customarrows.util.arrows.Repulsion.detonateFirework;

public class DimensionArrow extends CustomArrow {

    private List<UUID> hitEntities;

    public DimensionArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&8Dimension Arrow", "dimension_arrow",
                                List.of("", "This arrow temporarily teleports", "the target into another dimension")),
                        Color.GRAY));

        hitEntities = new ArrayList<>();
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        if (!(event.getEntity() instanceof LivingEntity livingEntity)) return;
        event.getDamager().remove();

        hitEntities.add(livingEntity.getUniqueId());
        Location startingLocation = livingEntity.getLocation();

        boolean end = ThreadLocalRandom.current().nextBoolean();
        long delay = 10 * 20;

        if (!teleportToOneOfDimensions(livingEntity, end)) {
            shooter.sendTitle("", color("&7Damn it, the arrow broke!"), 5, 20, 5);
            return;
        }

        detonateFirework(startingLocation, FireworkEffect.Type.BALL, Color.WHITE);

        Bukkit.getScheduler().runTaskLater(CustomArrows.getInstance(), () -> {
            livingEntity.teleport(startingLocation);
            hitEntities.remove(livingEntity.getUniqueId());
            detonateFirework(startingLocation, FireworkEffect.Type.BALL, Color.WHITE);

            if (livingEntity instanceof Player player)
                player.sendTitle("", color("&7I'm back!"), 5, 20, 5);
        }, delay);
    }


}
