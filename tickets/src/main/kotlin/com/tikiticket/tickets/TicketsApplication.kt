package com.tikiticket.tickets

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

//@EnableScheduling
@SpringBootApplication
class TicketsApplication

fun main(args: Array<String>) {
	runApplication<TicketsApplication>(*args)
}
