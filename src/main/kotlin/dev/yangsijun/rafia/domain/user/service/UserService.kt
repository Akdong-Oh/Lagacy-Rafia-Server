package dev.yangsijun.rafia.domain.user.service

import dev.yangsijun.rafia.domain.user.domain.User
import dev.yangsijun.rafia.domain.user.dto.CreateUser
import dev.yangsijun.rafia.domain.user.repository.UserRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.*

@Service
class UserService(private val userRepository: UserRepository) {
    suspend fun create(userDto: CreateUser): Mono<User> {
        return userRepository.existsByName(userDto.name)
            .flatMap { exists ->
                if (exists) {
                    Mono.error(IllegalArgumentException("Existed User, name: ${userDto.name}"))
                } else {
                    val user = User(UUID.randomUUID(), userDto.name)
                    userRepository.save(user)
                }
            }
    }

    suspend fun findById(id: UUID): Mono<User> {
        return userRepository.findById(id)
            .switchIfEmpty(Mono.error(IllegalArgumentException("Not Found User, id: $id")))
    }

    suspend fun update(user: User): Mono<User> {
        return userRepository.findById(user.id)
            .switchIfEmpty(Mono.error(IllegalArgumentException("Not Found User, id: $user.id")))
            .flatMap { existingUser ->
                val updatedUserWithId = User(existingUser.id, user.name)
                userRepository.save(updatedUserWithId)
            }
    }
}
