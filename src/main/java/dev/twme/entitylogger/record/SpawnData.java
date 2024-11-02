package dev.twme.entitylogger.record;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

/**
 * Data class for storing information about an entity spawn.
 */
public abstract class SpawnData {
    public final Entity entity;
    public final long spawnTime;
    public final Location spawnLocation;
    public SpawnData(Entity entity, long spawnTime, Location spawnLocation) {
        this.entity = entity;
        this.spawnTime = spawnTime;
        this.spawnLocation = spawnLocation;
    }
}
