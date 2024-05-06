package com.tikiticket.tickets.global.infrastructure.redis.map

import java.util.concurrent.TimeUnit

interface MapEntity<K, V> {
    val key: K
    val value: V
    val ttl: Long?
    val ttlUnit: TimeUnit?
}