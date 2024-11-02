package dev.twme.entitylogger.record;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

/**
 * Data class for storing information about an entity spawn.
 */
public abstract class SpawnData {

    /**
     * The entity that spawned.
     */
    public final Entity entity;

    /**
     * The time the entity spawned.
     */
    public final long spawnTime;
    
    /**
     * The location the entity spawned at.
     */
    public final Location spawnLocation;

    /**
     * Creates a new instance of the SpawnData class.
     * @param entity The entity that spawned.
     * @param spawnTime The time the entity spawned.
     * @param spawnLocation The location the entity spawned at.
     */
    public SpawnData(Entity entity, long spawnTime, Location spawnLocation) {
        this.entity = entity;
        this.spawnTime = spawnTime;
        this.spawnLocation = spawnLocation;
    }
}
