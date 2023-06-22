package dev.yangsijun.rafia.domain.room.api

import dev.yangsijun.rafia.domain.room.domain.Room
import dev.yangsijun.rafia.domain.room.dto.CreateRoom
import dev.yangsijun.rafia.domain.room.service.RoomService
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import java.util.*

@RestController
@RequestMapping("/api/room")
class RoomController(
    private val roomService: RoomService
) {
    @GetMapping("/rooms")
    suspend fun getAllRooms(): ResponseEntity<Flux<Room>> {
        return ResponseEntity.ok()
            .body(roomService.findAll())
    }

    @GetMapping("/{id}")
    suspend fun getRoomById(@PathVariable id: UUID): ResponseEntity<Room> {
        return ResponseEntity.ok()
            .body(roomService.findById(id).awaitSingle())
    }

    @PostMapping
    suspend fun createRoom(@RequestBody roomDto: CreateRoom): ResponseEntity<Room> {
        val createdUser = roomService.create(roomDto).awaitSingle()
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser)
    }

    @DeleteMapping
    suspend fun deleteRoom(@PathVariable id: UUID): ResponseEntity<Void> {
        roomService.deleteById(id).awaitSingle()
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }
}
