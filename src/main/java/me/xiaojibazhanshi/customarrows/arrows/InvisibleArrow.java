package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.List;

public class InvisibleArrow extends CustomArrow {

    public InvisibleArrow() {
        super(ArrowFactory.changeTippedColor // Or you can use #changeTippedEffect if you need the effect
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&7Invisible Arrow", "invisible_arrow",
                                List.of("", "This arrow is only visible", "to the player who shot it")),
                        Color.GRAY));
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        event.getEntity().remove();
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        if (!(event.getEntity() instanceof LivingEntity)) return;

        event.setDamage(event.getDamage() * 1.1);
    }

    @Override
    public void onShoot(EntityShootBowEvent event, Player shooter) {
        event.getProjectile().setVisibleByDefault(false);
        shooter.showEntity(CustomArrows.getInstance(), event.getProjectile());
    }
}
