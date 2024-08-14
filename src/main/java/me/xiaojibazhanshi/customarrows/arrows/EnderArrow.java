package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import me.xiaojibazhanshi.customarrows.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.List;

public class EnderArrow extends CustomArrow {

    public EnderArrow() {
        super(ArrowFactory.changeTippedEffect // Takes in an item and potion type
                (ArrowFactory.createArrowItemStack( // Takes in the material, name, id and the lore
                        Material.TIPPED_ARROW, "&0Ender arrow", "ender_arrow",
                        List.of("", "This arrow will teleport", "you to wherever it lands")),
                PotionType.WEAKNESS));
    }

    @Override
    public void onHitGround(ProjectileHitEvent event, Player shooter) {
        Location arrowLocation = event.getEntity().getLocation();

        handleTeleport(shooter, arrowLocation);
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        // The potion effect in this scenario is just to color the arrow, so I remove it
        Bukkit.getScheduler().runTaskLater(CustomArrows.getInstance(), () -> {
            ((LivingEntity) event.getEntity()).removePotionEffect(PotionEffectType.WEAKNESS);
        }, 10); // Doesn't work if I do it instantly

        Location arrowLocation = event.getEntity().getLocation();

        handleTeleport(shooter, arrowLocation);
    }

    private void handleTeleport(Player player, Location location) {
        player.sendMessage(Util.color("&7Whoosh...!"));
        player.teleport(location.setDirection(player.getLocation().getDirection()));
    }
}
