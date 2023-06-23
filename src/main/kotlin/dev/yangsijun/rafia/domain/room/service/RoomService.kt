package dev.yangsijun.rafia.domain.room.service

import dev.yangsijun.rafia.domain.room.domain.Room
import dev.yangsijun.rafia.domain.room.dto.CreateRoom
import dev.yangsijun.rafia.domain.room.enums.RoomStatus
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.ReactiveRedisOperations
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.time.LocalDateTime
import java.util.UUID

private const val ROOM: String = "room"

@Service
class RoomService(
    private val redisOperations: ReactiveRedisOperations<String, Room>
) {
    private val log = LoggerFactory.getLogger(javaClass)

    fun findAll(): Flux<Room> {
        return redisOperations.opsForHash<String, Room>().values(ROOM)
            .also { log.trace("RoomService#findAll : ${LocalDateTime.now()}") }
    }

    fun create(roomDto: CreateRoom): Mono<Room> {
        return findAll()
            .collectList()
            .flatMap { rooms ->
                val isDuplicateName = rooms.any { it.name == roomDto.name }
                if (isDuplicateName) {
                    Mono.error(IllegalArgumentException("Duplicate name : $roomDto.name"))
                } else {
                    val room = Room(name = roomDto.name, status = RoomStatus.OPEN)
                    // Mono<Room> 먼저 반환 이후, 데이터를 Redis에 저장
                    Mono.just(room)
                        .doOnSuccess {
                            redisOperations.opsForHash<String, Room>().put(ROOM, room.id, room)
                                .subscribeOn(Schedulers.boundedElastic())
                                .subscribe()
                            log.trace("RoomService#create [$roomDto.name] : ${LocalDateTime.now()}")
                        }
                }
            }
    }

    fun findById(id: UUID): Mono<Room> {
        return redisOperations.opsForHash<String, Room>().get(ROOM, id.toString())
            .switchIfEmpty(Mono.error(IllegalArgumentException("Not found Room, id : $id")))
            .also { log.trace("RoomService#findById [$id] : ${LocalDateTime.now()}") }
    }

    fun deleteById(id: UUID): Mono<Room> {
        return findById(id)
            .doOnSuccess {
                redisOperations.opsForHash<String, Room>().remove(ROOM, id.toString())
                    .subscribeOn(Schedulers.boundedElastic())
                    .subscribe()
                log.trace("RoomService#deleteById [$id] : ${LocalDateTime.now()}")
            }
    }
}
