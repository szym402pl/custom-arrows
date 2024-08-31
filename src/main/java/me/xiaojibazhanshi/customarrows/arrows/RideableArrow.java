package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.*;

import static me.xiaojibazhanshi.customarrows.util.arrows.Repulsion.detonateFirework;
import static me.xiaojibazhanshi.customarrows.util.arrows.Torch.sendLowerTitle;

public class RideableArrow extends CustomArrow {

    private final Map<UUID, Boolean> turboModeUsers = new HashMap<>();

    public RideableArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&8Rideable Arrow", "rideable_arrow",
                                List.of("", "This arrow is rideable. Yeah, you heard it right.")),
                        Color.fromRGB(169, 169, 169)));
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        Arrow arrow = (Arrow) event.getDamager();

        arrow.eject();
        arrow.remove();
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        Arrow arrow = (Arrow) event.getEntity();

        arrow.eject();
        arrow.remove();
    }

    @Override
    public void onShoot(EntityShootBowEvent event, Player shooter) {
        if (event.getBow() == null) return;

        UUID uuid = shooter.getUniqueId();
        turboModeUsers.putIfAbsent(uuid, false);

        Arrow arrow = (Arrow) event.getProjectile();
        Material weapon = event.getBow().getType();

        if (weapon == Material.BOW) {
            Boolean nextMode = !turboModeUsers.get(uuid);
            turboModeUsers.put(uuid, nextMode);

            String modePart = nextMode ? "&cTurbo" : "&aNormal";
            sendLowerTitle(shooter, "&7Mode: " + modePart);

            arrow.remove();

            ItemStack arrowItem = arrow.getItem();
            Inventory playerInventory = shooter.getInventory();

            playerInventory.addItem(arrowItem);

            return;
        }

        boolean isTurbo = turboModeUsers.get(uuid);

        Vector originalVelocity = arrow.getVelocity();
        Location arrowLocation = arrow.getLocation();

        double nonTurboMultiplier = 0.65;
        double turboMultiplier = 1.20;

        double velocityMultiplier = isTurbo ? turboMultiplier : nonTurboMultiplier;
        Vector newVelocity = originalVelocity.multiply(velocityMultiplier);

        arrow.setVelocity(newVelocity);
        arrow.addPassenger(shooter);

        Color color = isTurbo ? Color.RED : Color.GREEN;
        detonateFirework(arrowLocation, FireworkEffect.Type.BURST, color);
    }
}
