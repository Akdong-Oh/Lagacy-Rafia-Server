package dev.yangsijun.rafia.domain.room.domain

import dev.yangsijun.rafia.domain.room.enums.RoomStatus
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.redis.core.RedisHash
import java.util.*

@RedisHash(value = "room")
class Room(
    @Id
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val status: RoomStatus
    // TODO 그외 값 - 플레이어 수, 게임(게임 객체, 게임 시작 시 생성) 등
    //  그리고 게임 시작하고 나서 게임 정보 저장할때는 비동기로 해야함
    //  가져와서 정보수정 -> save() -> 리턴 -> 전달
    //                     ㄴ-> db에 저장
) {
    constructor() : this("", "", RoomStatus.OPEN)
}