package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LingeringPotion;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

import static me.xiaojibazhanshi.customarrows.util.ArrowSpecificUtil.spawnALingeringPotion;

public class AreaHealArrow extends CustomArrow {

    public AreaHealArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&aArea &cHeal &aArrow", "area_heal_arrow",
                                List.of("", "This arrow will turn the", "target into a hedgehog")),
                        Color.GREEN));
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        spawnALingeringPotion(event.getDamager());
        event.getDamager().remove();
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        spawnALingeringPotion(event.getEntity());
        event.getEntity().remove();
    }


}
