package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.*;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.List;

import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.color;
import static me.xiaojibazhanshi.customarrows.util.arrows.ArmorBreaker.getBaseMaterialFromArmor;
import static me.xiaojibazhanshi.customarrows.util.arrows.ArmorBreaker.hasEnchants;

public class ArmorBreakerArrow extends CustomArrow {

    enum HitArmorPiece {
        HELMET,
        CHESTPLATE,
        LEGGINGS,
        BOOTS;
    }

    public ArmorBreakerArrow() {
        super(ArrowFactory.changeTippedColor // Or you can use #changeTippedEffect if you need the effect
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&4Armor Breaker Arrow", "armor_breaker_arrow",
                                List.of("", "This arrow will break or weaken", "your target's hit armor piece")),
                        Color.RED));
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        if (!(event.getEntity() instanceof LivingEntity livingEntity)) return;
        if (livingEntity.getEquipment() == null) return;

        HitArmorPiece hitArmorPiece;
        int acquiredMaterialAmount;
        ItemStack targetItem;

        Arrow arrow = (Arrow) event.getDamager();

        Location hitLocation = arrow.getLocation();
        Location targetHeadLocation = livingEntity.getEyeLocation().add(0, 0.18, 0); // approximation
        double distanceFromTheTopOfTheHead = Math.abs(hitLocation.getY() - targetHeadLocation.getY());

        /* CHECKING FOR THE TARGET ARMOR PIECE */

        if (distanceFromTheTopOfTheHead > 1.10) {
            acquiredMaterialAmount = 4;
            hitArmorPiece = HitArmorPiece.BOOTS;
            targetItem = livingEntity.getEquipment().getBoots();

        } else if (distanceFromTheTopOfTheHead > 0.7) {
            acquiredMaterialAmount = 7;
            hitArmorPiece = HitArmorPiece.LEGGINGS;
            targetItem = livingEntity.getEquipment().getLeggings();

        } else if (distanceFromTheTopOfTheHead > 0.25) {
            acquiredMaterialAmount = 8;
            hitArmorPiece = HitArmorPiece.CHESTPLATE;
            targetItem = livingEntity.getEquipment().getChestplate();

        } else {
            acquiredMaterialAmount = 5;
            hitArmorPiece = HitArmorPiece.HELMET;
            targetItem = livingEntity.getEquipment().getHelmet();
        }

        if (targetItem == null) return;

        /* CHECKING FOR THE ENCHANTS AND PERFORMING SPECIFIC ACTION ON THE ARMOR PIECE */

        Damageable itemMeta = (Damageable) targetItem.getItemMeta();
        assert itemMeta != null;

        int damage = hasEnchants(targetItem) ? 35 : 50;
        int newItemDamage = itemMeta.getDamage() + damage;
        boolean breakArmor = newItemDamage > targetItem.getType().getMaxDurability();

        World world = livingEntity.getWorld();

        if (breakArmor) {
            ItemStack acquiredMaterials = new ItemStack(getBaseMaterialFromArmor(targetItem), acquiredMaterialAmount);

            world.dropItemNaturally(livingEntity.getLocation(), acquiredMaterials);
            targetItem.setAmount(0); // this effectively breaks the armor piece
        } else {
            itemMeta.setDamage(newItemDamage);
            targetItem.setItemMeta(itemMeta);
        }

        /* NOTIFYING PLAYERS ABOUT DAMAGES DONE */

        String armorPieceName = hitArmorPiece.name().toLowerCase();

        world.playSound(hitLocation,
                breakArmor ? Sound.ENTITY_ITEM_BREAK : Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR,
                0.3F, 0.3F);

        String shooterTitle = color("&7You have "
                + (!breakArmor ? "dealt &b" + damage + " &7damage to " : "broken ")
                + "&a{entity}&7's " + armorPieceName + "&7!");

        String entityName = livingEntity.getType().name().toLowerCase();

        if (livingEntity instanceof Player player) {
            String targetTitle = color("&c" + shooter.getName() + " &7has "
                    + (!breakArmor ? "dealt &b" + damage + " &7damage to your " : "broken your ")
                    + "&a" + armorPieceName + "&7!");

            entityName = player.getName();
            player.sendTitle("", targetTitle, 5, 20, 5);
        }

        shooter.sendTitle("", shooterTitle.replace("{entity}", entityName), 5, 20, 5);
    }
}
