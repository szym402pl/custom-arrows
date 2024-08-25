package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.List;

import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.color;

public class RiderArrow extends CustomArrow {

    public RiderArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&dRider Arrow", "rider_arrow",
                                List.of("", "This arrow will let you", "hop on top of any entity")),
                        Color.PURPLE));
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        if (!(event.getEntity() instanceof LivingEntity livingEntity)) return;
        event.getDamager().remove();

        int delay = 20 * 10;

        if (!livingEntity.addPassenger(shooter)) {
            shooter.sendTitle("", color("&7I cannot hop on top of it..."), 5, 25, 5);
            return;
        }

        shooter.sendTitle("", color("&7Yippee!"), 5, 25, 5);
        Bukkit.getScheduler().runTaskLater(CustomArrows.getInstance(), livingEntity::eject, delay);
    }
}
