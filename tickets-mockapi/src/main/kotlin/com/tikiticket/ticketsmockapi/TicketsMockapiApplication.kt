package com.tikiticket.ticketsmockapi

import com.github.tomakehurst.wiremock.WireMockServer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component


@SpringBootApplication
class TicketsMockapiApplication

fun main(args: Array<String>) {
	runApplication<TicketsMockapiApplication>(*args)
}

@Component
class WireMockStarter(private val wireMockServer: WireMockServer) : ApplicationListener<ContextRefreshedEvent> {
	private val log: Logger = LoggerFactory.getLogger(WireMockStarter::class.java)
	override fun onApplicationEvent(event: ContextRefreshedEvent) {
		log.info("[WireMockStarter] Starting WireMockServer...")
		wireMockServer.start()
		log.info("[WireMockStarter] WireMockServer started on port ${wireMockServer.port()}.")
	}
}