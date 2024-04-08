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
class BookingMockApiTest {
    @Value("\${tikitiket.url}")
    private lateinit var tikitiketUrl: String

    @Test
    fun `API 6  예매`() {
        val headers = HttpHeaders()
        headers.add("Content-Type", "application/json")
        headers.add("User-Id", "10000")

        val seatNo = 1
        val url = "$tikitiketUrl/bookings?seat_no=$seatNo"

        val requestEntity = HttpEntity<String>(headers)

        val restTemplate = TestRestTemplate()
        val result: ResponseEntity<Map<String, Any>> = restTemplate.exchange(
            url,
            HttpMethod.PATCH,
            requestEntity,
            object : ParameterizedTypeReference<Map<String, Any>>() {}
        )

        Assertions.assertEquals(HttpStatus.OK, result.statusCode)
        val responseBody = result.body ?: throw IllegalStateException("Response body is null")

        Assertions.assertTrue(responseBody.containsKey("booking_no"))
        Assertions.assertTrue(responseBody.containsKey("booking_status"))
        Assertions.assertTrue(responseBody.containsKey("concert_id"))
        Assertions.assertTrue(responseBody.containsKey("concert_name"))
        Assertions.assertTrue(responseBody.containsKey("concert_date"))
        Assertions.assertEquals(seatNo, responseBody["seat_no"])
        Assertions.assertTrue(responseBody.containsKey("ticket_price"))
        Assertions.assertTrue(responseBody.containsKey("booked_at"))

    }

    @Test
    fun `API 7  예매 내역 결제`() {
        val headers = HttpHeaders()
        headers.add("Content-Type", "application/json")
        headers.add("User-Id", "10000")

        val bookingNo = 2024040700001
        val url = "$tikitiketUrl/bookings/$bookingNo/purchase"

        val requestBody = "{\"payment_method\": \"MY_BALANCE\"}"
        val requestEntity = HttpEntity(requestBody, headers)

        val restTemplate = TestRestTemplate()
        val result: ResponseEntity<Map<String, Any>> = restTemplate.exchange(
            url,
            HttpMethod.PATCH,
            requestEntity,
            object : ParameterizedTypeReference<Map<String, Any>>() {}
        )

        Assertions.assertEquals(HttpStatus.OK, result.statusCode)
        val responseBody = result.body ?: throw IllegalStateException("Response body is null")
        Assertions.assertEquals(bookingNo, responseBody["booking_no"])
        Assertions.assertTrue(responseBody.containsKey("booking_status"))
        Assertions.assertTrue(responseBody.containsKey("concert_id"))
        Assertions.assertTrue(responseBody.containsKey("concert_name"))
        Assertions.assertTrue(responseBody.containsKey("concert_date"))
        Assertions.assertTrue(responseBody.containsKey("seat_no"))
        Assertions.assertTrue(responseBody.containsKey("ticket_price"))
        Assertions.assertTrue(responseBody.containsKey("booked_at"))
        Assertions.assertTrue(responseBody.containsKey("paid_at"))
    }

    @Test
    fun `API 8  예매 내역 조회`() {
        val headers = HttpHeaders()
        headers.add("User-Id", "10000")

        val requestEntity = HttpEntity<String>(headers)
        val bookingNo = 2024040700001
        val restTemplate = TestRestTemplate()
        val result: ResponseEntity<Map<String, Any>> = restTemplate.exchange(
            "$tikitiketUrl/bookings/$bookingNo",
            HttpMethod.GET,
            requestEntity,
            object : ParameterizedTypeReference<Map<String, Any>>() {}
        )

        Assertions.assertEquals(HttpStatus.OK, result.statusCode)
        val responseBody = result.body ?: throw IllegalStateException("Response body is null")
        Assertions.assertEquals(bookingNo, responseBody["booking_no"])
        Assertions.assertTrue(responseBody.containsKey("booking_status"))
        Assertions.assertTrue(responseBody.containsKey("concert_id"))
        Assertions.assertTrue(responseBody.containsKey("concert_name"))
        Assertions.assertTrue(responseBody.containsKey("concert_date"))
        Assertions.assertTrue(responseBody.containsKey("seat_no"))
        Assertions.assertTrue(responseBody.containsKey("ticket_price"))
        Assertions.assertTrue(responseBody.containsKey("booked_at"))
    }
}