package com.tikiticket.tickets.aggregate.payment.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.tikiticket.tickets.aggregate.payment.api.dto.MakePaymentRequest
import com.tikiticket.tickets.aggregate.payment.application.MakePaymentUseCase
import com.tikiticket.tickets.aggregate.payment.domain.Payment
import com.tikiticket.tickets.aggregate.payment.domain.PaymentMethodType
import com.tikiticket.tickets.aggregate.payment.domain.PaymentStatusType
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDateTime

@WebMvcTest(com.tikiticket.tickets.aggregate.payment.api.PaymentController::class)
class PaymentControllerTest
{
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @MockBean
    lateinit var makePaymentUseCase: MakePaymentUseCase

    /**
     *  API.8 예매 내역 결제
     */
    @Test
    fun `예매 내역 결제에 성공한다`() {
        val userId = "testUser1"
        val bookingId = 1L
        val currentTime = LocalDateTime.of(2024, 5, 2, 23, 30, 0)

        val payment = Payment(
            1L,
            PaymentMethodType.BALANCE,
            50000,
            userId,
            currentTime,
            PaymentStatusType.SUCCESS,
            currentTime,
            currentTime
        )

        given(makePaymentUseCase(any()))
            .willReturn(payment)

        val request = MakePaymentRequest(PaymentMethodType.BALANCE)

        mockMvc.perform(
            MockMvcRequestBuilders.patch("/payments/%d/pay".format(bookingId))
                .header("User-Id", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.paymentId").value("1"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.paymentMethod").value("BALANCE"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.paymentAmount").value("50000"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.payerId").value("testUser1"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.paymentDateTime").value("2024-05-02T23:30:00"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.paymentStatus").value("SUCCESS"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.updatedAt").value("2024-05-02T23:30:00"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.updatedAt").value("2024-05-02T23:30:00"))
    }
}