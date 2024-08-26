package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.List;

import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.color;

public class BetrayalArrow extends CustomArrow {

    public BetrayalArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&cBetrayal Arrow", "betrayal_arrow",
                                List.of("", "This arrow will turn all the", "nearby enemies on each other")),
                        Color.ORANGE));
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        Arrow arrow = (Arrow) event.getDamager();

        List<Monster> enemies = arrow.getNearbyEntities(6, 6, 6)
                .stream()
                .filter(monster -> monster instanceof LivingEntity)
                .filter(monster -> monster instanceof Monster)
                .map(monster -> (Monster) monster)
                .toList();

        if (enemies.size() < 2) return;

        for (int i = 0; i < enemies.size() -1; i++) {
            if (enemies.get(i + 1) == null) break;

            Monster enemy = enemies.get(i);
            Monster nextEnemy = enemies.get(i + 1);

            nextEnemy.setTarget(enemy);
        }

        arrow.remove();

        shooter.sendTitle("", color("&7They're turning on each other!"), 5, 25, 5);
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        Arrow arrow = (Arrow) event.getEntity();

        List<Monster> enemies = arrow.getNearbyEntities(6, 6, 6)
                .stream()
                .filter(monster -> monster instanceof LivingEntity)
                .filter(monster -> monster instanceof Monster)
                .map(monster -> (Monster) monster)
                .toList();

        if (enemies.size() < 2) return;

        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i + 1) == null) break;

            Monster enemy = enemies.get(i);
            Monster nextEnemy = enemies.get(i + 1);

            nextEnemy.setTarget(enemy);
        }

        arrow.remove();

        shooter.sendTitle("", color("&7They're turning on each other!"), 5, 25, 5);
    }
}
