//package dev.yangsijun.rafia.domain.room.api
//
//import org.springframework.messaging.handler.annotation.MessageMapping
//import org.springframework.messaging.simp.SimpMessageSendingOperations
//import org.springframework.web.bind.annotation.RestController
//
//
//@RestController
//class WSRoomController(
//    private val sendingOperations: SimpMessageSendingOperations
//) {
//    @MessageMapping("/chat/message")
//    fun enter(message: ChatMessage) {
//        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
//            message.setMessage(message.getSender() + "님이 입장하였습니다.")
//        }
//        sendingOperations.convertAndSend("/topic/chat/room/" + message.getRoomId(), message)
//    }
//
//}