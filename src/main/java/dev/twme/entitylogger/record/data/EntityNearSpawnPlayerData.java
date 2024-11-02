package dev.twme.entitylogger.record.data;

import dev.twme.entitylogger.record.SpawnData;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

/**
 * Data class for storing information about an entity spawn near a player.
 */
public class EntityNearSpawnPlayerData extends SpawnData {

    /**
     * The closest player to the entity.
     */
    public final Player closestPlayer;

    /**
     * The distance between the entity and the closest player.
     */
    public final double distance;

    /**
     * Creates a new instance of the EntitySpawnNearPlayerData class.
     * @param entity The entity that spawned.
     * @param closestPlayer The closest player to the entity.
     * @param spawnLocation The location the entity spawned at.
     * @param distance The distance between the entity and the closest player.
     * @param spawnTime The time the entity spawned.
     */
    public EntityNearSpawnPlayerData(Entity entity, Player closestPlayer, Location spawnLocation, double distance, long spawnTime) {
        super(entity, spawnTime, spawnLocation);
        this.closestPlayer = closestPlayer;
        this.distance = distance;
    }
}
