package me.xiaojibazhanshi.customarrows.arrows;

import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.ArrowFactory;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class HoneyTrapArrow extends CustomArrow {

    public HoneyTrapArrow() {
        super(new ItemStack(Material.ARROW));
        //super(ArrowFactory.createArrowItemStack(Material.TIPPED_ARROW, "&6Honey Trap Arrow"));
    }
}
