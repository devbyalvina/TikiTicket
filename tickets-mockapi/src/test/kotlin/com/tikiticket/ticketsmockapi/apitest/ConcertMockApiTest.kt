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
class ConcertMockApiTest {
    @Value("\${tikitiket.url}")
    private lateinit var tikitiketUrl: String

    @Test
    fun `API 4  콘서트 스케줄 목록 조회`() {
        val headers = HttpHeaders()
        headers.add("User-Id", "10000")

        val requestEntity = HttpEntity<String>(headers)

        val startDate = "2024-04-10"
        val endDate = "2024-04-25"

        val url = "$tikitiketUrl/concerts/schedules?start_date=$startDate&end_date=$endDate"

        val restTemplate = TestRestTemplate()
        val result: ResponseEntity<Map<String, List<Map<String, Any>>>> = restTemplate.exchange(
            url,
            HttpMethod.GET,
            requestEntity,
            object : ParameterizedTypeReference<Map<String, List<Map<String, Any>>>>() {}
        )

        Assertions.assertEquals(HttpStatus.OK, result.statusCode)
        val responseBody = result.body ?: throw IllegalStateException("Response body is null")
        val concerts = responseBody["concerts"]

        Assertions.assertNotNull(concerts)
        Assertions.assertEquals(5, concerts!!.size)
    }

    @Test
    fun `API 5  콘서트 좌석 목록 조회`() {
        val headers = HttpHeaders()
        headers.add("User-Id", "10000")

        val requestEntity = HttpEntity<String>(headers)

        val restTemplate = TestRestTemplate()
        val concertId = "1001" // 조회할 콘서트 ID
        val result: ResponseEntity<Map<String, Any>> = restTemplate.exchange(
            "$tikitiketUrl/concerts/$concertId/seats",
            HttpMethod.GET,
            requestEntity,
            object : ParameterizedTypeReference<Map<String, Any>>() {}
        )

        Assertions.assertEquals(HttpStatus.OK, result.statusCode)
        val responseBody = result.body ?: throw IllegalStateException("Response body is null")
        val seats = responseBody["seats"] as List<Map<String, Any>>

        Assertions.assertEquals(5, seats.size)
    }
}