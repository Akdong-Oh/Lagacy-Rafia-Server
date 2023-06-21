package dev.yangsijun.rafia.domain.user.api

import dev.yangsijun.rafia.domain.user.domain.User
import dev.yangsijun.rafia.domain.user.dto.CreateUser
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/user")
class UserController(private val userService: UserService) {
    @GetMapping("/{id}")
    suspend fun getUserById(@PathVariable id: UUID): ResponseEntity<User> {
        val user = userService.findById(id).awaitSingle()
        return ResponseEntity.ok(user)
    }

    @PostMapping
    suspend fun createUser(@RequestBody userDto: CreateUser): ResponseEntity<User> {
        val createdUser = userService.create(userDto).awaitSingle()
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser)
    }

    @PutMapping
    suspend fun updateUser(@RequestBody user: User): ResponseEntity<User> {
        val updated = userService.update(user).awaitSingle()
        return ResponseEntity.ok(updated)
    }
}
