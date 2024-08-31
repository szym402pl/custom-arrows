package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.material.Torch;

import java.util.List;

import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.color;
import static me.xiaojibazhanshi.customarrows.util.arrows.ArmorBreaker.getBaseMaterialFromArmor;
import static me.xiaojibazhanshi.customarrows.util.arrows.ArmorBreaker.hasEnchants;
import static me.xiaojibazhanshi.customarrows.util.arrows.Torch.sendLowerTitle;
import static me.xiaojibazhanshi.customarrows.util.arrows.Torch.setupTorchAt;

public class TorchArrow extends CustomArrow {

    public TorchArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&eTorch Arrow", "torch_arrow",
                                List.of("", "This arrow will place a torch for you")),
                        Color.YELLOW));
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        Block hitBlock = event.getHitBlock();

        if (hitBlock == null) {
            sendLowerTitle(shooter, "&7This isn't a block...");
            return;
        }

        Material type = hitBlock.getType();

        if (!type.isBlock() || !type.isSolid() || type.isAir()) {
            sendLowerTitle(shooter, "&7I can't place a torch here...");
            return;
        }

        BlockFace hitBlockFace = event.getHitBlockFace();

        if (hitBlockFace == null || hitBlockFace == BlockFace.DOWN) {
            sendLowerTitle(shooter, "&7I can't place a torch here...");
            return;
        }

        setupTorchAt(hitBlock, hitBlockFace);

        Arrow arrow = (Arrow) event.getEntity();
        arrow.remove();
    }


}
