package com.tikiticket.tickets.global.infrastructure.redis.configuration

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.kotlinModule
import com.tikiticket.tickets.aggregate.concert.domain.Concert
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration


@EnableCaching
@Configuration
class LettuceConfig {
//    @Bean
//    fun redisConnectionFactory(): RedisConnectionFactory {
//        return LettuceConnectionFactory(redisHost!!, redisPort)
//    }

    @Bean
    fun redisTemplate(connectionFactory: RedisConnectionFactory?): RedisTemplate<String, Any> {
        val redisTemplate = RedisTemplate<String, Any>()
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = GenericJackson2JsonRedisSerializer()
        redisTemplate.connectionFactory = connectionFactory
        return redisTemplate
    }

    @Bean(name = arrayOf("concertCacheManager"))
    fun cacheManager(connectionFactory: RedisConnectionFactory): CacheManager {
        val builder = RedisCacheManager.RedisCacheManagerBuilder
            .fromConnectionFactory(connectionFactory)
        val configuration = RedisCacheConfiguration.defaultCacheConfig()
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer()))
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(Jackson2JsonRedisSerializer(objectMapper(), Concert::class.java)))
            .entryTtl(Duration.ofMinutes(30))
        builder.cacheDefaults(configuration)
        return builder.build()
    }

    @Bean
    fun objectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper().apply {
            setSerializationInclusion(JsonInclude.Include.NON_NULL)
            registerModule(JavaTimeModule())
            registerModule(kotlinModule())    // @JsonProperty 안 써도 되게 하는 코틀린 모듈
        }
        return objectMapper
    }
}