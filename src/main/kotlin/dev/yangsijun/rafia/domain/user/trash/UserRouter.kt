package dev.yangsijun.rafia.domain.user.trash//package dev.yangsijun.rafia.domain.user.api
//
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.http.MediaType.APPLICATION_JSON
//import org.springframework.web.reactive.function.server.RequestPredicates.*
//import org.springframework.web.reactive.function.server.RouterFunction
//import org.springframework.web.reactive.function.server.RouterFunctions.nest
//import org.springframework.web.reactive.function.server.RouterFunctions.route
//import org.springframework.web.reactive.function.server.ServerResponse
//import org.springframework.web.reactive.function.server.coRouter
//
//@Configuration
//class UserRouter(handler: UserHandler) {
////    @Bean
////    fun route(userHandler: UserHandler): RouterFunction<ServerResponse> {
////        return coRouter {
////            "/user".nest {
////                GET("/{id}", userHandler::find)
////                POST("/", userHandler::create)
////                PUT("/", userHandler::update)
////            }
////            GET("/test", userHandler::test)
////        }
////    }
//
//    @Bean
//    fun routerFunction(handler: UserHandler?): RouterFunction<ServerResponse> {
//        return route
//    }
//
//
//    val route = coRouter {
//        "/person".nest {
//            GET("/test",handler::test)
//            accept(APPLICATION_JSON).nest {
//                GET("/{id}", handler::find)
//            }
//        }
//    }
//}