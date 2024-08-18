package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import me.xiaojibazhanshi.customarrows.util.GeneralUtil;
import org.bukkit.*;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class GrapplingHookArrow extends CustomArrow {

    private Set<UUID> playersUsingArrow = new HashSet<>();

    public GrapplingHookArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&9Grappling Hook Arrow", "grappling_hook_arrow",
                                List.of("", "This arrow will grapple you", "up to wherever it lands")),
                Color.BLUE));
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {

        Bukkit.getScheduler().runTaskLater(CustomArrows.getInstance(), () -> {
            playersUsingArrow.remove(shooter.getUniqueId());

            event.getEntity().eject();
            event.getEntity().remove();
        }, 40);
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {

        Bukkit.getScheduler().runTaskLater(CustomArrows.getInstance(), () -> {
            playersUsingArrow.remove(shooter.getUniqueId());

            event.getDamager().eject();
            event.getDamager().remove();
            shooter.eject();
        }, 40);
    }

    @Override
    public void onShoot(EntityShootBowEvent event, Player shooter) {
        float force = event.getForce();

        if (force < 2.0) {
            event.setCancelled(true);
            shooter.sendTitle("", GeneralUtil.color("&7I need to draw the bow further..."));
            return;
        }

        if (playersUsingArrow.contains(shooter.getUniqueId())) {
            event.setCancelled(true);
            shooter.sendTitle("", GeneralUtil.color("&7I'll wait for the last hook to fall"));
            return;
        }

        Entity arrow = event.getProjectile();
        World world = shooter.getWorld();

        Location arrowLocation = arrow.getLocation();

        arrow.setVelocity(arrow.getVelocity().multiply(0.5));
        playersUsingArrow.add(shooter.getUniqueId());

        Chicken arrowHolder = createInvisibleChicken(arrowLocation, world);
        arrowHolder.setLeashHolder(shooter);

        GeneralUtil.removeEntityAfter(arrowHolder, 200);
        arrow.addPassenger(arrowHolder);
    }

    @Override
    public void onPlayerLeave(PlayerQuitEvent event, Player player) {
        playersUsingArrow.remove(player.getUniqueId());
        player.eject();
    }

    private Chicken createInvisibleChicken(Location location, World world) {
        Chicken chicken = (Chicken) world.spawnEntity(location, EntityType.CHICKEN);
        chicken.setGravity(false);
        chicken.setAI(false);
        chicken.setInvulnerable(true);
        chicken.setVisibleByDefault(false);

        return chicken;
    }
}
