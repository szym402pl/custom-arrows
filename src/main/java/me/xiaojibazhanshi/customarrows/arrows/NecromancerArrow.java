package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.Vector;

import java.util.List;

import static me.xiaojibazhanshi.customarrows.util.arrows.Necromancer.convertToUndead;
import static me.xiaojibazhanshi.customarrows.util.arrows.Necromancer.spawnOneOfSelected;

public class NecromancerArrow extends CustomArrow {

    public NecromancerArrow() {
        super(ArrowFactory.changeTippedColor // Or you can use #changeTippedEffect if you need the effect
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&2Necromancer Arrow", "necromancer_arrow",
                                List.of("", "This arrow has a few uses:", "1) Turn villagers into undead ones",
                                        "2) Spawn a monster next to a player", "3) Heal monsters for a period of time")),
                        Color.GREEN));
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        Location hitLocation = event.getEntity().getLocation();
        event.getEntity().remove();

        spawnOneOfSelected(List.of(EntityType.ZOMBIE, EntityType.SKELETON, EntityType.SPIDER), hitLocation);
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        if (!(event.getEntity() instanceof LivingEntity hitEntity)) return;

        if (hitEntity instanceof Monster) {
            event.setDamage(0);

            double maxHealth = hitEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
            boolean moreThanMaxHP = hitEntity.getHealth() * 1.5 > maxHealth;

            hitEntity.setHealth(moreThanMaxHP ? maxHealth : hitEntity.getHealth() * 1.5);

            hitEntity.getWorld().spawnParticle(Particle.HAPPY_VILLAGER,
                    hitEntity.getEyeLocation().add(new Vector(0, 1, 0)),
                    4);

            return;
        }

        if (hitEntity instanceof IronGolem) {
            int ironGolemDamageMultiplier = 2;

            event.setDamage(event.getDamage() * ironGolemDamageMultiplier);
            return;
        }

        if (hitEntity.getType() == EntityType.VILLAGER) {
            convertToUndead((Villager) hitEntity);
            return;
        }

        spawnOneOfSelected(List.of(EntityType.ZOMBIE, EntityType.SKELETON, EntityType.SPIDER), hitEntity.getLocation());
    }
}
