package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class AntiGravityArrow extends CustomArrow {

    public AntiGravityArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                               Material.TIPPED_ARROW, "&8Anti-Gravity Arrow", "anti_gravity_arrow",
                               List.of("", "This arrow will make sure your", "enemies feel the power of anti-gravity")),
                        Color.fromRGB(169, 169, 169))); // Dark gray again
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        if (!(event.getEntity() instanceof LivingEntity entity)) return;

        PotionEffect effect = new PotionEffect(PotionEffectType.LEVITATION, 5 * 20, 1, true);
        entity.addPotionEffect(effect);
    }


}
