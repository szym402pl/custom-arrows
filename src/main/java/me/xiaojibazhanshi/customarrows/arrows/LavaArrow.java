package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.color;

public class LavaArrow extends CustomArrow {

    public LavaArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&cLava Arrow", "lava_arrow",
                                List.of("", "This arrow is made", "out of literal lava")),
                        Color.ORANGE));
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        Block block = event.getHitBlock();
        Arrow arrow = (Arrow) event.getEntity();

        if (block == null || !block.getType().isSolid()) return;

        arrow.remove();
        block.setType(Material.LAVA);
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        if (!(event.getEntity() instanceof LivingEntity entity)) return;
        if (entity.getEquipment() == null) return;

        Location hitLocation = event.getDamager().getLocation();
        event.getDamager().remove();
        shooter.getWorld().spawnParticle(Particle.LAVA, hitLocation, 3, 0.5, 0.1, 0.5);

        if (entity.getEquipment().getArmorContents().length == 0) {

            Bukkit.getScheduler().runTaskLater(CustomArrows.getInstance(), () -> {
                entity.setVisualFire(true);
            }, 1);

            entity.damage(1.0);

            Bukkit.getScheduler().runTaskLater(CustomArrows.getInstance(), () -> {
                entity.setVisualFire(false);
            }, 11);

            return;
        }

        boolean hasMetalArmorPieces = false;

        for (ItemStack equipmentItem : entity.getEquipment().getArmorContents()) {
            if (equipmentItem == null) continue;

            String itemName = equipmentItem.getType().name();

            if (itemName.contains("NETHERITE") || itemName.contains("IRON")) {
                hasMetalArmorPieces = true;

                break;
            }
        }

        if (!hasMetalArmorPieces) return;

        entity.damage(2.0);

        if (!(entity instanceof Player player)) return;

        player.sendTitle(color("&cOuch!"),
                color("&7A &cLava Arrow &7just hit my metal armor!"), 5, 20, 5);

    }
}

