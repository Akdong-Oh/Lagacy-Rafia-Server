package dev.yangsijun.rafia.domain.room.api

import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.HtmlUtils


@Controller
class WSRoomController {
//    @MessageMapping("/chat")
//    fun enter(message: String) {
//        return sendingOperations.convertAndSend("/topic/messages","test")
//    }
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    fun greeting(message: String): String {
        Thread.sleep(1000)
        return "STASDASDASDASDDSADSADSDDSADSADA"
    }
}