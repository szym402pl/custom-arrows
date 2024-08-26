package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.checkerframework.checker.units.qual.N;

import java.util.*;

import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.color;
import static me.xiaojibazhanshi.customarrows.util.arrows.AimAssist.provideAimAssist;
import static me.xiaojibazhanshi.customarrows.util.arrows.Homing.findEntityInSight;

public class NamingArrow extends CustomArrow {

    private final Map<UUID, LivingEntity> beingNamed;

    public NamingArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&dNaming Arrow", "naming_arrow",
                                List.of("", "This arrow lets you name any entity",
                                        "", "NOTE: Can't be used on players")),
                        Color.PURPLE));

        beingNamed = new HashMap<>();

    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        shooter.sendTitle("", color("&7I can't name a block..."), 5, 25, 5);
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        Entity entity = event.getEntity();
        event.setDamage(0);

        if (!(entity instanceof LivingEntity livingEntity)) {
            shooter.sendTitle("", color("&7I can't name this..."), 5, 25, 5);
            return;
        }

        if (entity instanceof Player) {
            shooter.sendTitle("", color("&7I can't name them..."), 5, 25, 5);
            return;
        }

        event.setCancelled(true);

        Arrow arrow = (Arrow) event.getDamager();
        arrow.remove();

        UUID uuid = shooter.getUniqueId();

        beingNamed.remove(uuid);
        beingNamed.put(uuid, livingEntity);

        String entityName = livingEntity.getType().toString().toLowerCase();
        shooter.sendTitle("", color("&7What should I name the " + entityName + "?"), 5, 25, 5);

        long delayBeforeRemoved = 20 * 20;

        Bukkit.getScheduler().runTaskLater(CustomArrows.getInstance(), () -> {
            if (!beingNamed.containsKey(uuid)) return;

            shooter.sendTitle("", color("&7I took too long naming it..."), 5, 25, 5);
            beingNamed.remove(uuid);
        }, delayBeforeRemoved);

    }

    @Override
    public void onPlayerLeave(PlayerQuitEvent event, Player player) {
        UUID uuid = player.getUniqueId();

        beingNamed.remove(uuid);
    }

    @Override
    public void onPlayerChat(AsyncPlayerChatEvent event, Player player) {
        UUID uuid = player.getUniqueId();
        if (!(beingNamed.containsKey(uuid))) return;

        event.setCancelled(true);

        String rawMessage = event.getMessage();
        String name = color(rawMessage);

        LivingEntity entity = beingNamed.get(uuid);
        entity.setCustomNameVisible(true);
        entity.setCustomName(name);

        beingNamed.remove(uuid);
    }
}
