package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.runnables.SlimeArmyTask;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.List;

public class SlimeArmyArrow extends CustomArrow {

    public SlimeArmyArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&2Slime Army Arrow", "slime_army_arrow",
                                List.of("", "This arrow can either", "bless or curse you")),
                        Color.OLIVE));
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        Entity entity = event.getEntity();
        if (!(entity instanceof LivingEntity livingEntity)) return;
        Arrow arrow = (Arrow) event.getDamager();
        arrow.remove();

        executeSlimeArmyTask(livingEntity, null);
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        Block hitBlock = event.getHitBlock();
        if (hitBlock == null) return;

        Block blockAbove = hitBlock.getRelative(BlockFace.UP);
        if (blockAbove.getType() == Material.WATER) return;

        Arrow arrow = (Arrow) event.getEntity();
        arrow.remove();

        Location customLocation = hitBlock.getLocation();

        executeSlimeArmyTask(shooter, customLocation);
    }

    private void executeSlimeArmyTask(LivingEntity entity, Location location) {
        int slimeAmount = 5;
        int period = 10;

        SlimeArmyTask task = new SlimeArmyTask(entity, slimeAmount, location);
        Bukkit.getScheduler().runTaskTimer(CustomArrows.getInstance(), task, 1, period);
    }
}
