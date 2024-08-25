package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static me.xiaojibazhanshi.customarrows.util.arrows.ImmunityBubble.generateSpherePoints;
import static me.xiaojibazhanshi.customarrows.util.arrows.ImmunityBubble.runBubbleTask;

public class ImmunityBubbleArrow extends CustomArrow {

    public static final Set<UUID> invincibleEntities = new HashSet<>();

    public ImmunityBubbleArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&9Immunity Bubble Arrow", "immunity_bubble_arrow",
                                List.of("", "This arrow will create an", "immunity bubble where it lands")),
                        Color.BLUE));
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        if (!(event.getEntity() instanceof LivingEntity livingEntity)) return;

        PotionEffect potionEffect = new PotionEffect(PotionEffectType.ABSORPTION, 100, 2, true);
        livingEntity.addPotionEffect(potionEffect);
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        Arrow arrow = (Arrow) event.getEntity();

        arrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
        arrow.setVisibleByDefault(false);

        int radius = 4;
        double density = 8.0;
        int durationInSeconds = 10;

        List<Location> points = generateSpherePoints(arrow.getLocation(), radius, density);

        runBubbleTask(arrow, points, invincibleEntities, durationInSeconds, radius);
    }
}
