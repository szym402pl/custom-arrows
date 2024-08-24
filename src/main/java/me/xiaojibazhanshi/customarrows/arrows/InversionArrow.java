package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import me.xiaojibazhanshi.customarrows.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.List;

public class InversionArrow extends CustomArrow {

    public InversionArrow() {
        super(ArrowFactory.changeTippedColor // Or you can use #changeTippedEffect if you need the effect
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&eInversion Arrow", "inversion_arrow",
                                List.of("", "This arrow will rotate the", "target by 180 degrees")),
                        Color.YELLOW));
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        if (!(event.getEntity() instanceof LivingEntity target)) return;

        Location newLocation = target.getLocation().clone();
        newLocation.setYaw(target.getLocation().getYaw() + 180.0F);

        target.teleport(newLocation);

        if (target instanceof Player player) sendRotatedTitle(player);
    }

    private void sendRotatedTitle(Player player) {
        player.sendTitle(GeneralUtil.color("&7Get rotated"), "", 10, 15, 5);

        Bukkit.getScheduler().runTaskLater(CustomArrows.getInstance(), () -> {
            player.sendTitle("", GeneralUtil.color("&7&o...idiot"), 15, 10, 5);
        }, 30);
    }
}
