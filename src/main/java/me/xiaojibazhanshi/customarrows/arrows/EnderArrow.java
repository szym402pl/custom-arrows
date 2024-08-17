package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import me.xiaojibazhanshi.customarrows.util.GeneralUtil;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.List;

public class EnderArrow extends CustomArrow {

    public EnderArrow() {
        super(ArrowFactory.changeTippedColor // Or you can use #changeTippedEffect if you need the effect
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&0Ender Arrow", "ender_arrow",
                                List.of("", "This arrow will teleport", "you to wherever it lands")),
                Color.BLACK));
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        Location arrowLocation = event.getEntity().getLocation();

        handleTeleport(shooter, arrowLocation);
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        Location arrowLocation = event.getEntity().getLocation();

        handleTeleport(shooter, arrowLocation);
    }

    // This method is pretty simple, so I didn't put it in the ArrowSpecificUtil class
    private void handleTeleport(Player player, Location location) {
        player.sendMessage(GeneralUtil.color("&7Whoosh...!"));
        player.teleport(location.setDirection(player.getLocation().getDirection()));
    }
}
