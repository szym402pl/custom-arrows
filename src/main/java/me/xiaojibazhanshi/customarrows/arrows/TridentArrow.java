package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.Vector;

import java.util.List;

import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.removeEntityAfter;

public class TridentArrow extends CustomArrow {

    public TridentArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&fTrident Arrow", "trident_arrow",
                                List.of("", "This arrow launches a trident", "in the looked at direction")),
                        Color.WHITE));
    }

    @Override
    public void onShoot(EntityShootBowEvent event, Player shooter) {
        Arrow arrow = (Arrow) event.getProjectile();
        Vector arrowVelocity = arrow.getVelocity();
        long maxLifeTime = 20 * 10;

        arrow.remove();

        Trident trident = shooter.launchProjectile(Trident.class);
        trident.setVelocity(arrowVelocity);
        trident.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);

        removeEntityAfter(trident, maxLifeTime);
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        Arrow arrow = (Arrow) event.getEntity();

        arrow.remove();
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        double newDamage = event.getDamage() * 1.25;
        event.setDamage(newDamage);

        Arrow arrow = (Arrow) event.getDamager();
        arrow.remove();
    }
}
