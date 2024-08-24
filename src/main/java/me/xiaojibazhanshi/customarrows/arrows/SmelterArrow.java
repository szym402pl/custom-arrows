package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import me.xiaojibazhanshi.customarrows.util.GeneralUtil;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.getEnchantLevel;
import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.hasEnchant;
import static me.xiaojibazhanshi.customarrows.util.arrows.Smelter.getSmeltedMaterial;
import static me.xiaojibazhanshi.customarrows.util.arrows.VeinMiner.isOre;

public class SmelterArrow extends CustomArrow {

    public SmelterArrow() {
        super(ArrowFactory.changeTippedColor // Or you can use #changeTippedEffect if you need the effect
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&fSmelter Arrow", "smelter_arrow",
                                List.of("", "This arrow will instantly", "smelt whatever ore it lands on")),
                        Color.SILVER));
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        Block block = event.getHitBlock();
        if (block == null) return;
        if (!isOre(block)) return;

        event.getEntity().remove();

        Material smeltedMaterial = getSmeltedMaterial(block);

        Enchantment enchant = Enchantment.FORTUNE;
        ItemStack droppedItem = new ItemStack(smeltedMaterial);

        for (ItemStack item : shooter.getInventory().getContents()) {
            if (!hasEnchant(item, enchant)) continue;

            int nextAmount = (1 + (getEnchantLevel(item, enchant)));

            if (droppedItem.getAmount() < nextAmount) {
                droppedItem.setAmount(nextAmount);
            }
        }

        block.setType(Material.AIR);

        Location location = block.getLocation();
        World world = location.getWorld();
        assert world != null;

        world.dropItemNaturally(location, droppedItem);
        world.spawn(location, ExperienceOrb.class);
        world.spawnParticle(Particle.LAVA, location.add(0, 1,0), 10, 0.5, 0, 0.5);
    }






}
