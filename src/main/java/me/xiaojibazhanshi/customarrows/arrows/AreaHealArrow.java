package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.List;

import static me.xiaojibazhanshi.customarrows.util.arrows.AreaHeal.spawnALingeringPotion;

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
