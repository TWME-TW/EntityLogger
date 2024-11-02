package dev.twme.entitylogger.listener;

import dev.twme.entitylogger.EntityLogger;
import dev.twme.entitylogger.record.data.EntitySpawnData;
import dev.twme.entitylogger.record.data.EntityNearSpawnPlayerData;
import dev.twme.entitylogger.record.pool.EntityNearPlayerSpawnPool;
import dev.twme.entitylogger.record.pool.EntitySpawnPool;
import dev.twme.entitylogger.util.PersistentKeys;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.persistence.PersistentDataType;

/**
 * Listener for entity spawn events
 */
public class EntitySpawnListener implements Listener {

    /**
     * Logger instance
     */
    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        var entity = event.getEntity();
        if (entity.getType() == EntityType.ITEM) {
            return;
        }

        var location = event.getLocation();
        var spawnTime = System.currentTimeMillis();

        var persistentDataContainer = entity.getPersistentDataContainer();

        var distance = -1D;
        Player closestPlayer = null;

        /*
         * Find the closest player to the entity
         */
        for (Player player : entity.getLocation().getNearbyPlayers(30)) {

            var playerDistance = player.getLocation().distance(entity.getLocation());
            if (distance == -1 || playerDistance < distance) {
                distance = playerDistance;
                closestPlayer = player;
            }
        }

        if (closestPlayer != null) {

            /*
             * Store the closest player data in the entity's persistent data container
             */
            persistentDataContainer.set(PersistentKeys.ClosestPlayerID, PersistentDataType.STRING, closestPlayer.getName());
            persistentDataContainer.set(PersistentKeys.ClosestPlayerUUID, PersistentDataType.STRING, closestPlayer.getUniqueId().toString());
            persistentDataContainer.set(PersistentKeys.ClosestPlayerDistance, PersistentDataType.DOUBLE, distance);
            persistentDataContainer.set(PersistentKeys.EntitySpawnTime, PersistentDataType.LONG, spawnTime);

            /*
             * Store the entity spawn data in the player spawn pool
             */
            var data = new EntityNearSpawnPlayerData(entity, closestPlayer, location, distance, spawnTime);
            EntityNearPlayerSpawnPool.addToPool(data);
        } else {

            /*
             * Store the entity spawn time in the entity's persistent data container
             */
            persistentDataContainer.set(PersistentKeys.EntitySpawnTime, PersistentDataType.LONG, spawnTime);

            /*
             * Store the entity spawn data in the entity spawn pool
             */
            var data = new EntitySpawnData(entity, spawnTime, location);
            EntitySpawnPool.addToPool(data);
        }
    }
}
