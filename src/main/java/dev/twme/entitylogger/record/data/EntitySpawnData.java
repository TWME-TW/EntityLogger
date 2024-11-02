package dev.twme.entitylogger.record.data;

import dev.twme.entitylogger.record.SpawnData;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

/**
 * Data class for storing information about an entity spawn. but don't have player near.
 */
public class EntitySpawnData extends SpawnData {

    /**
     * Creates a new instance of the EntitySpawnData class.
     * @param entity The entity that spawned.
     * @param spawnTime The time the entity spawned.
     * @param spawnLocation The location the entity spawned at.
     */
    public EntitySpawnData(Entity entity, long spawnTime, Location spawnLocation) {
        super(entity, spawnTime, spawnLocation);
    }
}
