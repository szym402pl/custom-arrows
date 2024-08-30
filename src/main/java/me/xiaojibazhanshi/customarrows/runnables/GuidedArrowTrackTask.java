package me.xiaojibazhanshi.customarrows.runnables;

import me.xiaojibazhanshi.customarrows.CustomArrows;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.function.Consumer;

import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.color;
import static me.xiaojibazhanshi.customarrows.util.arrows.Fishing.*;

public class GuidedArrowTrackTask implements Consumer<BukkitTask> {

    private final Arrow arrow;
    private final Player shooter;
    private final double startingSpeed;

    public GuidedArrowTrackTask(Arrow arrow, Player shooter) {
        this.arrow = arrow;
        this.shooter = shooter;
        startingSpeed = arrow.getVelocity().length();
    }

    @Override
    public void accept(BukkitTask bukkitTask) {
        Location arrowLocation = arrow.getLocation();
        Location playerLocation = shooter.getLocation();

        Vector playerToArrow = arrowLocation.toVector().subtract(playerLocation.toVector());
        double arrowDistance = playerToArrow.length();

        if (arrow.isDead() || arrow.isOnGround() || !shooter.isOnline() || arrowDistance > 75) {

            if (!arrow.isDead())
                arrow.setGlowing(false);

            sendActionBarMessage(shooter, color("&cLost contact with the arrow..."));

            bukkitTask.cancel();
            return;
        }

        Vector playerDirection = playerLocation.getDirection().normalize();

        double newDistance = arrowDistance + 2;
        Location targetLocation = playerLocation.clone().add(playerDirection.multiply(newDistance));

        Vector newDirection = targetLocation.toVector().subtract(arrowLocation.toVector()).normalize();
        arrow.setVelocity(newDirection.multiply(startingSpeed));

        String formattedDistance = String.format("%.2f", newDistance);

        sendActionBarMessage(shooter,
                color("&7Guiding the arrow... &8| &7Distance: &b" + formattedDistance + " &7blocks"));
    }

    private void sendActionBarMessage(Player player, String message) {
        TextComponent textComponent = new TextComponent(message);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, textComponent);
    }

}
