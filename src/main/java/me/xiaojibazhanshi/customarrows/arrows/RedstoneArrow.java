package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.List;

import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.color;
import static me.xiaojibazhanshi.customarrows.util.arrows.Redstone.getRedstonePoweredDevices;
import static me.xiaojibazhanshi.customarrows.util.arrows.Redstone.handleRedstoneDevice;

public class RedstoneArrow extends CustomArrow {

    public RedstoneArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&4Redstone Arrow", "redstone_arrow",
                                List.of("", "This arrow will power anything", "that needs redstone to function")),
                        Color.RED));
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        Block hitBlock = event.getHitBlock();
        if (hitBlock == null) return;

        List<Material> redstonePoweredDevices = getRedstonePoweredDevices();
        Material hitBlockMaterial = hitBlock.getType();

        if (!redstonePoweredDevices.contains(hitBlockMaterial)) {
            shooter.sendTitle("", color("&7This isn't redstone powered..."), 5, 25, 5);
            return;
        }

        Arrow arrow = (Arrow) event.getEntity();
        arrow.remove();

        handleRedstoneDevice(redstonePoweredDevices, hitBlock);
    }
}
