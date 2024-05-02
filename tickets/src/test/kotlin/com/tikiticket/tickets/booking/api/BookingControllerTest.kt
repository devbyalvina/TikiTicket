package com.tikiticket.tickets.booking.api

import com.tikiticket.tickets.booking.application.MakeBookingUseCase
import com.tikiticket.tickets.booking.domain.Booking
import com.tikiticket.tickets.booking.domain.BookingStatusType
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDateTime

@WebMvcTest(BookingController::class)
class BookingControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var makeBookingUseCase: MakeBookingUseCase

    /**
     *  API.6 예매
     */
    @Test
    fun `예매에 성공한다`() {
        val userId = "testUser1"
        val concertId = 1L
        val seatId = 100L

        val currentTime = LocalDateTime.of(2024, 5, 2, 14, 30, 0)

        val booking = Booking (
            1L,
            userId,
            BookingStatusType.BOOKED,
            currentTime.plusMinutes(5),
            concertId,
            "테스트 콘서트1",
            "가수1",
            LocalDateTime.of(2024, 5, 30, 10, 30, 0),
            "콘서트 홀1",
            seatId,
            10,
            100000L,
            currentTime,
            currentTime
        )

        given(makeBookingUseCase(any()))
            .willReturn(booking)

        mockMvc.perform(
            patch("/bookings")
                .header("User-Id", userId)
                .param("concert_id", concertId.toString())
                .param("seat_id", seatId.toString())
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.bookerId").value("testUser1"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.bookingStatus").value("BOOKED"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.expiryDateTime").value("2024-05-02T14:35:00"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.concertId").value("1"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.concertName").value("테스트 콘서트1"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.artistName").value("가수1"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.concertDate").value("2024-05-30T10:30:00"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.venue").value("콘서트 홀1"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.seatId").value("100"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.seatNo").value("10"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.ticketPrice").value("100000"))
    }
}