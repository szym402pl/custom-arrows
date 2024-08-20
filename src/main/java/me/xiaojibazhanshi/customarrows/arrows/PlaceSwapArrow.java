package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import me.xiaojibazhanshi.customarrows.util.GeneralUtil;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;

import java.util.List;

public class PlaceSwapArrow extends CustomArrow {

    public PlaceSwapArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&cPlace Swap Arrow", "place_swap_arrow",
                                List.of("", "This arrow will swap your", "and enemy's locations")),
                        Color.RED));
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        event.setCancelled(true);

        Entity hitEntity = event.getEntity();
        Location hitEntityLocation = hitEntity.getLocation();
        Location shooterLocation = shooter.getLocation();

        event.getEntity().remove();

        hitEntity.teleport(shooterLocation);
        shooter.teleport(hitEntityLocation);

        int time = 10;
        shooter.sendTitle("", GeneralUtil.color("&7Woah, I really did swap places..."), time, time, time);
    }

    @Override
    public void onShoot(EntityShootBowEvent event, Player shooter) {
        Arrow arrow = (Arrow) event.getProjectile();

        arrow.setVisibleByDefault(false);
        shooter.showEntity(CustomArrows.getInstance(), arrow);
    }
}
