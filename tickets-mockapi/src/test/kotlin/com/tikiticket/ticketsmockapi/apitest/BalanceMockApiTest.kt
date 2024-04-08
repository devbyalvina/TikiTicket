package com.tikiticket.ticketsmockapi.apitest

import com.tikiticket.ticketsmockapi.WireMockConfig
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
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
class BalanceMockApiTest {
    @Value("\${tikitiket.url}")
    private lateinit var tikitiketUrl: String

    @Test
    fun `API 9  잔고 조회`() {
        val headers = HttpHeaders()
        headers.add("User-Id", "10000")

        val requestEntity = HttpEntity<String>(headers)

        val restTemplate = TestRestTemplate()
        val result: ResponseEntity<Map<String, Any>> = restTemplate.exchange(
            "$tikitiketUrl/balances",
            HttpMethod.GET,
            requestEntity,
            object : ParameterizedTypeReference<Map<String, Any>>() {}
        )

        Assertions.assertEquals(HttpStatus.OK, result.statusCode)
        Assertions.assertEquals("10000", result.body?.get("user_id"))
        Assertions.assertEquals(45000, result.body?.get("balance"))
    }

    @Test
    fun `API 10  잔고 변경`() {
        val headers = HttpHeaders()
        headers.add("Content-Type", "application/json")
        headers.add("User-Id", "10000")

        val requestBody = "{\"transaction_type\": \"CHARGE\", \"amount\": 5000}"
        val requestEntity = HttpEntity(requestBody, headers)

        val restTemplate = TestRestTemplate()
        val result: ResponseEntity<Map<String, Any>> = restTemplate.exchange(
            "$tikitiketUrl/balances",
            HttpMethod.PATCH,
            requestEntity,
            object : ParameterizedTypeReference<Map<String, Any>>() {}
        )

        Assertions.assertEquals(HttpStatus.OK, result.statusCode)
        Assertions.assertEquals("10000", result.body?.get("user_id"))
        Assertions.assertEquals("55000", result.body?.get("balance"))
    }
}