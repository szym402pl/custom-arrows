package me.xiaojibazhanshi.customarrows.listeners;

import me.xiaojibazhanshi.customarrows.managers.ArrowManager;
import me.xiaojibazhanshi.customarrows.objects.CustomArrow;
import me.xiaojibazhanshi.customarrows.util.GeneralUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;

public class PlayerLeaveListener implements Listener {

    private final ArrowManager arrowManager;

    public PlayerLeaveListener(ArrowManager arrowManager) {
        this.arrowManager = arrowManager;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        List<CustomArrow> arrowsExecutingPQE = new ArrayList<>();
        CustomArrow stealthArrow = retrieveCustomArrow("stealth_arrow");

        if (stealthArrow != null) {
            arrowsExecutingPQE.add(stealthArrow);
        }

        for (CustomArrow customArrow : arrowsExecutingPQE) {
            customArrow.onPlayerLeave(event, event.getPlayer());
        }
    }

    private CustomArrow retrieveCustomArrow(String id) {
        if (arrowManager.getCustomArrows().containsKey(GeneralUtil.createStringNSKey(id))) {
            return arrowManager.getCustomArrows().get(GeneralUtil.createStringNSKey(id));
        }

        return null;
    }

}
