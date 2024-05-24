package com.tikiticket.tickets.global.infrastructure.redis.configuration

import jakarta.annotation.PreDestroy
import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import redis.embedded.RedisServer
import java.io.File
import java.net.InetSocketAddress
import java.net.Socket

@Configuration
class RedissonConfig{
    @Value("\${spring.data.redis.host}")
    private val redisHost: String? = null

    @Value("\${spring.data.redis.port}")
    private val redisPort = 0

    private val REDISSON_HOST_PREFIX = "redis://"

    private var port = redisPort

//    @Bean
//    fun redisConnectionFactory(redissonClient: RedissonClient): RedisConnectionFactory {
//        return RedissonConnectionFactory(redissonClient)
//    }

    @Bean
    fun redissonClient(): RedissonClient? {
        var redisson: RedissonClient? = null
        val config: Config = Config()
        config.useSingleServer()
            .setAddress("$REDISSON_HOST_PREFIX$redisHost:$port")
            .setDnsMonitoringInterval(-1)
        redisson = Redisson.create(config)
        return redisson
    }


//    private val redisServer: RedisServer =
//        if (isArmMac()) {
//            RedisServer(getPort(), redisForArmMac())
//        } else {
//            RedisServer(getPort())
//        }

    private val redisServer: RedisServer =
        if (isArmMac()) {
            RedisServer.newRedisServer()
                .executableProvider { redisForArmMac() }
                .port(getPort())
                .setting("maxmemory 128M")
                .setting("appendonly no")
                .setting("daemonize no")
                .build()
        } else {
            RedisServer.newRedisServer()
                .port(getPort())
                .setting("maxmemory 128M")
                .setting("appendonly no")
                .setting("daemonize no")
                .build()
        }

    init {
        redisServer.start()
    }

    private fun getPort(): Int {
        for (p in 10000..65535) {
            if (isAvailablePort(p)) {
                port = p
                return p
            }
        }

        throw IllegalArgumentException("Not Found Available port")
    }

    private fun isAvailablePort(port: Int): Boolean {
        return runCatching {
            Socket().use { socket ->
                socket.connect(InetSocketAddress("localhost", port), 1000)
                false
            }
        }.getOrElse { true }
    }

    private fun isArmMac(): Boolean {
        return System.getProperty("os.arch") == "aarch64" &&
                System.getProperty("os.name") == "Mac OS X"
    }

    private fun redisForArmMac(): File {
        return ClassPathResource("redis/redis-server-7.2.3-mac-arm64").getFile()
    }

    @PreDestroy
    fun stop() {
        this.redisServer.stop()
    }
}