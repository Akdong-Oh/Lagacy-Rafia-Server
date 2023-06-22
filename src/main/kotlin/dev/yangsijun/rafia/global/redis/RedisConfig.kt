package dev.yangsijun.rafia.global.redis

import dev.yangsijun.rafia.domain.room.domain.Room
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisOperations
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer


@Configuration
@EnableRedisRepositories
class RedisConfig {
    // spring boot는 - RedisReactiveAutoConfiguration 에서 다 등록해줌
    // https://umbum.dev/1045

    @Bean
    fun roomRedisTemplate(
        connectionFactory: ReactiveRedisConnectionFactory
    ): ReactiveRedisOperations<String, Room> {
        val serializationContext: RedisSerializationContext<String, Room> = RedisSerializationContext
            .newSerializationContext<String, Room>(StringRedisSerializer())
            .hashKey(StringRedisSerializer())
            .hashValue(Jackson2JsonRedisSerializer(Room::class.java))
            .build()
        return ReactiveRedisTemplate(connectionFactory, serializationContext)
    }
}