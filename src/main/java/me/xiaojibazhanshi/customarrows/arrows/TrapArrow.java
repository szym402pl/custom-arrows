package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import me.xiaojibazhanshi.customarrows.util.GeneralUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.List;

import static me.xiaojibazhanshi.customarrows.util.arrows.Trap.isValidTrapLocation;

public class TrapArrow extends CustomArrow {

    public TrapArrow() {
        super(ArrowFactory.changeTippedColor
                (ArrowFactory.createArrowItemStack(
                                Material.TIPPED_ARROW, "&0Trap Arrow", "trap_arrow",
                                List.of("", "This arrow will create a trap", "using tnt wherever it lands",
                                        "", "Note: if directly hits a player,", "it'll immobilize him using cobwebs")),
                        Color.BLACK));
    }

    @Override
    public void onHitBlock(ProjectileHitEvent event, Player shooter) {
        Block block = event.getHitBlock();

        if (!isValidTrapLocation(block)) {
            shooter.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                    TextComponent.fromLegacy(GeneralUtil.color("&7I can't set up a trap there...")));
            return;
        }

        event.getEntity().remove();

        Block above = block.getRelative(BlockFace.UP);
        above.setType(Material.STONE_PRESSURE_PLATE);

        Block below = block.getRelative(BlockFace.DOWN);
        below.setType(Material.TNT);
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event, Player shooter) {
        Location entityLocation = event.getEntity().getLocation();
        World world = entityLocation.getWorld();
        assert world != null;

        Block block = world.getBlockAt(entityLocation.add(0, 1, 0));
        if (block.getType() != Material.AIR) return;

        block.setType(Material.COBWEB);
    }


}
