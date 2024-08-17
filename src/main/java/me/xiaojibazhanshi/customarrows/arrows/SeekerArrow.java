package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.runnables.SeekerArrowRunnable;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.List;

public class SeekerArrow extends CustomArrow {

    public SeekerArrow() {
        super(ArrowFactory.changeTippedColor // Or you can use #changeTippedEffect if you need the effect
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&9Seeker Arrow", "seeker_arrow",
                                List.of("", "This arrow, whilst flying,", "will seek for targets below",
                                        "", "Note: It doesn't work on targets that", "are too close to the arrow's flying path!")),
                Color.NAVY));
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        event.getEntity().remove();
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        event.getEntity().setGlowing(false);

        Entity arrow = event.getDamager();

        arrow.remove();
        arrow.getWorld().createExplosion(arrow.getLocation(), 2.0F, false, true);
    }

    @Override
    public void onShoot(EntityShootBowEvent event, Player shooter) {
        SeekerArrowRunnable runnable = new SeekerArrowRunnable();
        Entity projectile = event.getProjectile();

        runnable.start(shooter, projectile, projectile.getVelocity());
    }
}
