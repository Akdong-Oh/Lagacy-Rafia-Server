package dev.yangsijun.rafia.domain.room.service

import dev.yangsijun.rafia.domain.room.domain.Room
import dev.yangsijun.rafia.domain.room.dto.CreateRoom
import dev.yangsijun.rafia.domain.room.enums.RoomStatus
import org.springframework.data.redis.core.ReactiveRedisOperations
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

private const val ROOM: String = "room"

@Service
class RoomService(
    private val redisOperations: ReactiveRedisOperations<String, Room>
) {
    fun findAll(): Flux<Room> {
        return redisOperations.opsForHash<String, Room>().values(ROOM)
    }

    fun create(roomDto: CreateRoom): Mono<Room> {
        return findAll()
            .collectList()
            .flatMap { rooms ->
                val isDuplicateName = rooms.any { it.name == roomDto.name }
                if (isDuplicateName) {
                    Mono.error(IllegalArgumentException("Duplicate name found: $roomDto.name"))
                } else {
                    val room = Room(name = roomDto.name, status = RoomStatus.OPEN)
                    redisOperations.opsForHash<String, Room>().put(ROOM, room.id, room)
                        .then(Mono.just(room))
                }
            }
    }

    fun findById(id: UUID): Mono<Room> {
        return redisOperations.opsForHash<String, Room>().get(ROOM, id.toString())
    }

    fun deleteById(id: UUID): Mono<Void> {
        return redisOperations.opsForHash<String, Room>().remove(ROOM, id.toString())
            .flatMap { Mono.empty() }
    }
}