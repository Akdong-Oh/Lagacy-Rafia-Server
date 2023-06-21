package dev.yangsijun.rafia.domain.user.trash//package dev.yangsijun.rafia.domain.user.api
//
//import dev.yangsijun.rafia.domain.user.domain.User
//import dev.yangsijun.rafia.domain.user.dto.CreateUser
//import dev.yangsijun.rafia.domain.user.repository.UserRepository
//import org.springframework.http.MediaType
//import org.springframework.stereotype.Component
//import org.springframework.web.reactive.function.server.*
//import org.springframework.web.reactive.function.server.ServerResponse.created
//import org.springframework.web.reactive.function.server.ServerResponse.ok
//import reactor.core.publisher.Mono
//import java.util.*
//
//@Component
//class UserHandler(
//    private val userRepository: UserRepository,
//) {
//    suspend fun create(request: ServerRequest): ServerResponse {
//        val user: Mono<CreateUser> = request.bodyToMono(CreateUser::class.java)
//        user
//            .flatMap { u ->
//                if (userRepository.existsByName(u.name).equals(false))
//                    Mono.error(RuntimeException("Existed User, name: $u.name"))
//                else
//                    userRepository.save(User(UUID.randomUUID(), u.name))
//            }
//        return created(request.uri()).buildAndAwait()
//    }
//
//    // TODO #bodyAndAwait 는 Flow 만 아규먼트로 받음
//    suspend fun find(request: ServerRequest): ServerResponse {
//        val id: UUID = UUID.fromString(request.pathVariable("id"))
//        val user: Mono<User> = userRepository.findById(id)
//            .switchIfEmpty(Mono.error(RuntimeException("Not Found User, id: $id")))
//        return ok().contentType(MediaType.APPLICATION_JSON).bodyValueAndAwait(user)
//    }
//
//    suspend fun update(request: ServerRequest): ServerResponse {
//        val id: UUID = UUID.fromString(request.pathVariable("id"))
//        val user: Mono<User> = request.bodyToMono(User::class.java)
//        user
//            .flatMap { u -> userRepository.findById(u.id) }
//            .switchIfEmpty(Mono.error(RuntimeException("Not Found User, id: $id")))
//            .flatMap { u -> userRepository.save(User(u.id, u.name)) }
//        return created(request.uri()).buildAndAwait()
//    }
//
//    suspend fun test(request: ServerRequest): ServerResponse {
//        return created(request.uri()).buildAndAwait()
//    }
//}