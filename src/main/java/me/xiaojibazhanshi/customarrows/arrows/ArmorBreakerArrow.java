package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.*;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.List;
import java.util.Locale;

import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.color;
import static me.xiaojibazhanshi.customarrows.util.arrows.AimAssist.provideAimAssist;
import static me.xiaojibazhanshi.customarrows.util.arrows.ArmorBreaker.getBaseMaterialFromArmor;
import static me.xiaojibazhanshi.customarrows.util.arrows.ArmorBreaker.hasEnchants;
import static me.xiaojibazhanshi.customarrows.util.arrows.Homing.findEntityInSight;

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
        Location targetEyeLocation = livingEntity.getEyeLocation();
        double distanceFromEyeLevel = hitLocation.distance(targetEyeLocation);

        /* CHECKING FOR THE TARGET ARMOR PIECE */

        if (distanceFromEyeLevel > 1.40) {
            acquiredMaterialAmount = 4;
            hitArmorPiece = HitArmorPiece.BOOTS;
            targetItem = livingEntity.getEquipment().getBoots();

        } else if (distanceFromEyeLevel > 1.0) {
            acquiredMaterialAmount = 7;
            hitArmorPiece = HitArmorPiece.LEGGINGS;
            targetItem = livingEntity.getEquipment().getLeggings();

        } else if (distanceFromEyeLevel > 0.4) {
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

        boolean hasEnchants = hasEnchants(targetItem);

        if (hasEnchants) {
            Damageable itemMeta = (Damageable) targetItem.getItemMeta();
            assert itemMeta != null;

            itemMeta.setDamage(itemMeta.getDamage() + 50);
        } else {
            targetItem.setAmount(0); // this effectively breaks the armor piece

            ItemStack acquiredMaterials = new ItemStack(getBaseMaterialFromArmor(targetItem), acquiredMaterialAmount);

            World world = livingEntity.getWorld();
            world.dropItemNaturally(livingEntity.getLocation(), acquiredMaterials);
            world.playSound(livingEntity.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
        }

        if (livingEntity instanceof Player player)  {
            String armorPieceName = hitArmorPiece.name().toLowerCase();

            String title = color("&c" + shooter.getName() + " &7has "
                    + (hasEnchants ? "dealt &b50 &7damage to your " : "broken your ") + "&a" + armorPieceName + "&7!");

            player.sendTitle("", title, 5, 20, 5);
        }

    }




}
