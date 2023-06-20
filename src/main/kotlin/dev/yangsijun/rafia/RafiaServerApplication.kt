package dev.yangsijun.rafia

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RafiaServerApplication

fun main(args: Array<String>) {
	runApplication<RafiaServerApplication>(*args)
}
