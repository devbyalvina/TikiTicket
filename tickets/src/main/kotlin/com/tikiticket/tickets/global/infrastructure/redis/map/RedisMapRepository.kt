package com.tikiticket.tickets.global.infrastructure.redis.map

interface RedisMapRepository<K, V> {
    fun findByKey(mapName: String, key: K): V?
    fun save(mapName: String, mapEntity: MapEntity<K, V>)
}