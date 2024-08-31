package me.xiaojibazhanshi.customarrows.util.arrows;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;

import static me.xiaojibazhanshi.customarrows.util.GeneralUtil.color;

public class Torch {

    private Torch() {

    }

    public static void sendLowerTitle(Player player, String text) {
        player.sendTitle("", color(text), 5, 25 ,5);
    }

    public static void setupTorchAt(Block origin, BlockFace targetBlockFace) {
        Block relative = origin.getRelative(targetBlockFace);
        relative.setType(Material.TORCH);

        if (targetBlockFace == BlockFace.UP) return;

        Directional nativeWallTorchData = (Directional) Material.WALL_TORCH.createBlockData();

        nativeWallTorchData.setFacing(targetBlockFace);
        relative.setBlockData(nativeWallTorchData);
    }

}
