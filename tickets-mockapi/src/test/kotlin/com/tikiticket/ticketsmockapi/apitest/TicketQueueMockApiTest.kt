package com.tikiticket.ticketsmockapi.apitest

import com.tikiticket.ticketsmockapi.WireMockConfig
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.*
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@TestPropertySource(locations=["classpath:application-mockapitest.properties"])
@ContextConfiguration(classes = [WireMockConfig::class])
class TicketQueueMockApiTest {
    private val log: Logger = LoggerFactory.getLogger(WireMockConfig::class.java)

    @Value("\${tikitiket.url}")
    private lateinit var tikitiketUrl: String

    @Test
    fun `API 1  대기열 토큰 발급`() {
        val headers = HttpHeaders()
        headers.add("Content-Type", "application/json")
        headers.add("User-Id", "10000")

        val requestEntity = HttpEntity<String>(headers)

        val restTemplate = TestRestTemplate()
        val result: ResponseEntity<Map<String, Any>> = restTemplate.exchange(
            "$tikitiketUrl/ticket-queue/tokens",
            HttpMethod.POST,
            requestEntity,
            object : ParameterizedTypeReference<Map<String, Any>>() {}
        )

        Assertions.assertEquals(HttpStatus.OK, result.statusCode)
        Assertions.assertEquals("10000", result.body?.get("user_id"))
        Assertions.assertEquals(5, result.body?.get("position"))
        Assertions.assertEquals("2024-04-05T10:30:00", result.body?.get("created_at"))
        Assertions.assertEquals("2024-04-05T10:40:00", result.body?.get("expiry_datetime"))
    }

    @Test
    fun `API 2  대기열 토큰 상태 변경`() {
        val headers = HttpHeaders()
        headers.add("Content-Type", "application/json")
        headers.add("User-Id", "10000")

        val requestBody = "{\"status\": \"ACTIVE\"}"
        val requestEntity = HttpEntity(requestBody, headers)

        val restTemplate = TestRestTemplate()
        val result: ResponseEntity<Map<String, Any>> = restTemplate.exchange(
            "$tikitiketUrl/ticket-queue/tokens/status",
            HttpMethod.PATCH,
            requestEntity,
            object : ParameterizedTypeReference<Map<String, Any>>() {}
        )

        Assertions.assertEquals(HttpStatus.OK, result.statusCode)
        Assertions.assertEquals("10000", result.body?.get("user_id"))
        Assertions.assertEquals("ACTIVE", result.body?.get("status"))
    }

    @Test
    fun `API 3  대기열 순번 조회`() {
        val headers = HttpHeaders()
        headers.add("User-Id", "10000")

        val requestEntity = HttpEntity<String>(headers)

        val restTemplate = TestRestTemplate()
        val result: ResponseEntity<Map<String, Any>> = restTemplate.exchange(
            "$tikitiketUrl/tickets/ticket-queue/positions",
            HttpMethod.GET,
            requestEntity,
            object : ParameterizedTypeReference<Map<String, Any>>() {}
        )

        Assertions.assertEquals(HttpStatus.OK, result.statusCode)
        Assertions.assertEquals("10000", result.body?.get("user_id"))
        Assertions.assertEquals(5, result.body?.get("position"))
        Assertions.assertEquals("2024-04-05T10:30:00", result.body?.get("created_at"))
        Assertions.assertEquals("2024-04-05T10:40:00", result.body?.get("expiry_datetime"))
    }
}