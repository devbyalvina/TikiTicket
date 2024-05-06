package com.tikiticket.tickets.global.infrastructure.redis.map.redisson

import com.tikiticket.tickets.global.infrastructure.redis.map.MapEntity
import com.tikiticket.tickets.global.infrastructure.redis.map.RedisMapRepository
import org.redisson.api.RMapCache
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component

@Component
class RedissonMapRepository<K, V> (
    private val redissonClient: RedissonClient
): RedisMapRepository<K, V> {
    override fun findByKey(mapName: String, key: K): V? {
        val mapCache: RMapCache<K, V>? = redissonClient.getMapCache(mapName)
        return mapCache?.get(key)
    }

    override fun save(mapName: String, mapEntity: MapEntity<K, V>) {
        val mapCache: RMapCache<K, V>? = redissonClient.getMapCache(mapName)

        when {
            mapEntity.ttl != null && mapEntity.ttlUnit != null && mapEntity.ttl!! > 0 -> {
                mapCache?.put(mapEntity.key, mapEntity.value, mapEntity.ttl!!, mapEntity.ttlUnit)
            }
            else -> {
                mapCache?.put(mapEntity.key, mapEntity.value)
            }
        }
    }
}