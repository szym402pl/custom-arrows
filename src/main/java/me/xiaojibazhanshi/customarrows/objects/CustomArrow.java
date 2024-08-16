package me.xiaojibazhanshi.customarrows.objects;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class CustomArrow {

    private final ItemStack arrowItem;

    public CustomArrow(ItemStack itemStack) {
        this.arrowItem = itemStack;
    }

    public ItemStack getArrowItem() {
        return arrowItem;
    }

    // default logic below
    // note: the shooter is ALWAYS a player

    // event listeners themselves will take care of
    // checking if it was the custom arrow that was shot

    public void onHitGround(ProjectileHitEvent event, Player shooter) {
        // shooter.sendMessage(ChatColor.GREEN + "You missed, haha!");
    }

    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        // shooter.sendMessage(ChatColor.GREEN + "Nice aim!");
    }

    public void onShoot(EntityShootBowEvent event, Player shooter) {
        // Here you can check if it's bow, crossbow, whatever
    }

    public void onPlayerLeave(PlayerQuitEvent event, Player player) {
        // Do whatever
    }
}
