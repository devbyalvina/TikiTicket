package com.tikiticket.tickets

import com.tikiticket.tickets.global.infrastructure.redis.configuration.LettuceConfig
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.context.annotation.Import
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(LettuceConfig::class)
abstract class RedisTestSupport {
    @Autowired
    lateinit var redisConnectionFactory: RedisConnectionFactory

    @BeforeEach
    fun setup() {
        redisConnectionFactory.connection.commands().flushAll()
    }
}