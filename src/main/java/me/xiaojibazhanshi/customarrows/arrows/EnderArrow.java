package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class EnderArrow extends CustomArrow {

    public EnderArrow() {
        super(ArrowFactory.changeTippedEffect
                (ArrowFactory.createArrowItemStack(Material.TIPPED_ARROW, "&0Ender arrow", "ender_arrow"),
                        PotionType.SLOWNESS));
    }

    @Override
    public void onHitGround(ProjectileHitEvent event, Player shooter) {
        super.onHitGround(event, shooter);
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        ((LivingEntity) event.getEntity()).removePotionEffect(PotionEffectType.SLOWNESS);

        shooter.teleport(event.getDamager().getLocation());
    }
}
