package dev.twme.entitylogger.listener;

import dev.twme.entitylogger.util.PersistentKeys;
import dev.twme.entitylogger.util.ToolUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.RayTraceResult;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Listener for player right click events
 */
public class PlayerRightClickListener implements Listener {
    /**
     * Handle player right click events
     * @param event The event
     */
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_AIR) {
            return;
        }

        var player = event.getPlayer();
        var item = player.getInventory().getItem(event.getHand());

        if (!ToolUtil.isTool(item)) {
            return;
        }

        event.setCancelled(true);

        if (!player.hasPermission("entitylogger.check")) {
            player.sendMessage("You do not have permission to use this tool");
        }

        var rayTraceResult = player.getWorld().rayTraceEntities(player.getEyeLocation(), player.getEyeLocation().getDirection(), 8,0.5);

        if (rayTraceResult == null) {
            player.sendMessage("No entity found");
            return;
        }

        var entity = rayTraceResult.getHitEntity();

        if (entity == null) {
            player.sendMessage("No entity found");
            return;
        }

        if (entity.getPersistentDataContainer().has(PersistentKeys.ClosestPlayerID)) {
            var closestPlayerID = entity.getPersistentDataContainer().get(PersistentKeys.ClosestPlayerID, PersistentDataType.STRING);
            var closestPlayerUUID = entity.getPersistentDataContainer().get(PersistentKeys.ClosestPlayerUUID, PersistentDataType.STRING);
            var closestPlayerDistance = entity.getPersistentDataContainer().get(PersistentKeys.ClosestPlayerDistance, PersistentDataType.DOUBLE);

            Date date = new Date(entity.getPersistentDataContainer().get(PersistentKeys.EntitySpawnTime, PersistentDataType.LONG));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/HH:mm:ss.SS");

            player.sendMessage("[" + entity.getType() + "] " + "Closest player to this entity is " + closestPlayerID + " with UUID " + closestPlayerUUID + " and distance " + String.format("%.2f", closestPlayerDistance) + " blocks" + " and spawn time " + simpleDateFormat.format(date));
        } else {
            player.sendMessage("[" + entity.getType() + "] " + "No closest player data found for this entity");
        }
    }
}
