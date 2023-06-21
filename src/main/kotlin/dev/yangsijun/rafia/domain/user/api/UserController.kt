package dev.yangsijun.rafia.domain.user.api

import dev.yangsijun.rafia.domain.user.domain.User
import dev.yangsijun.rafia.domain.user.dto.CreateUser
import dev.yangsijun.rafia.global.security.JwtManager
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.Disposable
import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping("/api/user")
class UserController(
    private val userService: UserService,
    private val jwtManager: JwtManager
) {
    @GetMapping("/{id}")
    suspend fun getUserById(@PathVariable id: UUID): ResponseEntity<Map<String, String>> {
        val token: Map<String, String> = userService.findById(id)
            .flatMap { user -> Mono.just(mapOf("token" to jwtManager.generateJwtToken(user))) }
            .awaitSingle()
        return ResponseEntity.ok().body(token)
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
