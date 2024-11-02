package dev.twme.entitylogger.record.pool;

import dev.twme.entitylogger.record.data.EntitySpawnData;
import org.bukkit.entity.EntityType;

import java.util.*;

/**
 * A pool of spawn data for a specific entity type.
 */
public class EntitySpawnPool {
    /*
     * entity spawn pools
     */
    private static final Map<EntityType, EntitySpawnPool> pools = new HashMap<>();

    /**
     * The spawn data for the pool.
     */
    private final EntityType type;

    private final List<EntitySpawnData> spawnData = new ArrayList<>();
    private long oldestSpawnTime = Long.MAX_VALUE;
    private long lastSpawnTime = 0;
    private long newestSpawnTimeDifference = 0;
    private int count = 0;

    public EntitySpawnPool(EntityType type) {
        this.type = type;
    }

    /**
     * Adds a spawn data to the pool.
     * @param data The spawn data to add.
     */
    public static void addToPool(EntitySpawnData data) {
        var type = data.entity.getType();
        if (!pools.containsKey(type)) {
            pools.put(type, new EntitySpawnPool(type));
        }
        pools.get(type).addSpawnData(data);
    }

    /**
     * Gets the entity spawn pools.
     * @return The entity spawn pools.
     */
    public static Map<EntityType, EntitySpawnPool> getPools() {
        return pools;
    }

    /**
     * Removes a entity spawn pool.
     * @param type The type of the entity to remove the pool for.
     */
    public static void removePool(EntityType type) {
        pools.remove(type);
    }

    /**
     * Gets the type of the entity.
     * @return The type of the entity.
     */
    public EntityType getType() {
        return type;
    }

    /**
     * Adds a spawn data to the pool.
     * @param data The spawn data to add.
     */
    private void addSpawnData(EntitySpawnData data) {
        spawnData.add(data);
        oldestSpawnTime = Math.min(data.spawnTime, oldestSpawnTime);
        if (lastSpawnTime == 0) {
            lastSpawnTime = data.spawnTime;
        }
        newestSpawnTimeDifference = data.spawnTime - lastSpawnTime;
        lastSpawnTime = data.spawnTime;
        count++;
    }

    /**
     * Gets the spawn data for the pool.
     * @return The spawn data for the pool.
     */
    public List<EntitySpawnData> getSpawnData() {
        return spawnData;
    }

    /**
     * Gets the time difference between the oldest and newest spawn.
     * @return The time difference between the oldest and newest spawn.
     */
    public long getSpawnTimeDifference() {
        return lastSpawnTime - oldestSpawnTime;
    }

    /**
     * Gets the difference between the newest spawn time and the last spawn time.
     * @return The difference between the newest spawn time and the last spawn time.
     */
    public long getNewestSpawnTimeDifference() {
        return newestSpawnTimeDifference;
    }

    /**
     * Gets the count of spawn data in the pool.
     * @return The count of spawn data in the pool.
     */
    public int getCount() {
        return count;
    }

    /**
     * Gets the oldest spawn time.
     * @return The oldest spawn time.
     */
    public long getOldestSpawnTime() {
        return oldestSpawnTime;
    }
}
