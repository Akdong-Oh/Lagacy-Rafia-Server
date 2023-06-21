package dev.yangsijun.rafia.domain.user.repository

import dev.yangsijun.rafia.domain.user.domain.User
import org.reactivestreams.Publisher
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import java.util.UUID

@Repository
interface UserRepository : ReactiveMongoRepository<User, UUID> {
    fun existsByName(name: String): Mono<Boolean>
}