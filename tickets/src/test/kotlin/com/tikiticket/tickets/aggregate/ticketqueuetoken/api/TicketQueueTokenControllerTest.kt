package com.tikiticket.tickets.aggregate.ticketqueuetoken.api

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime

@WebMvcTest(com.tikiticket.tickets.aggregate.ticketqueuetoken.api.TicketQueueTokenController::class)
class TicketQueueTokenControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @MockBean
    lateinit var createTokenUseCase: com.tikiticket.tickets.aggregate.ticketqueuetoken.application.CreateTokenUseCase

    @MockBean
    lateinit var modifyUserTokenStatusUseCase: com.tikiticket.tickets.aggregate.ticketqueuetoken.application.ModifyUserTokenStatusUseCase

    @MockBean
    lateinit var getUserTokenPositionUseCase: com.tikiticket.tickets.aggregate.ticketqueuetoken.application.GetUserTokenPositionUseCase

    /**
     *  API.1 대기열 토큰 발급
     */
    @Test
    fun `대기열 토큰 발급에 성공한다`() {
        val userId = "testUser"
        val currentTime = LocalDateTime.of(2024, 5, 2, 4, 0, 0)

        val ticketQueueToken = com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueToken(
            1,
            userId,
            com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TokenStatusType.WAITING,
            currentTime.plusMinutes(5),
            currentTime,
            currentTime
        )

        given(createTokenUseCase.invoke(eq(userId), any()))
            .willReturn(ticketQueueToken)

        mockMvc.perform(
            post("/ticket-queue-tokens")
                .header("User-Id", userId)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.tokenId").value(1))
            .andExpect(jsonPath("$.userId").value("testUser"))
            .andExpect(jsonPath("$.tokenStatus").value("WAITING"))
            .andExpect(jsonPath("$.expiryDateTime").value("2024-05-02T04:05:00"))
    }

    /**
     *  API.2 대기열 토큰 상태 변경
     */
    @Test
    fun `대기열 토큰 상태 변경에 성공한다`() {
        val userId = "testUser2"
        val createdTime = LocalDateTime.of(2024, 5, 2, 4, 0, 0)
        val modifiedTime =  LocalDateTime.of(2024, 5, 2, 4, 0, 30)

        val ticketQueueToken = com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueToken(
            1,
            userId,
            com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TokenStatusType.ACTIVE,
            createdTime.plusMinutes(5),
            createdTime,
            modifiedTime
        )

        given(modifyUserTokenStatusUseCase(any()))
            .willReturn(ticketQueueToken)

        val request =
            com.tikiticket.tickets.aggregate.ticketqueuetoken.api.dto.ModifyUserTokenStatusRequest(com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TokenStatusType.ACTIVE)

        mockMvc.perform(
            patch("/ticket-queue-tokens/status")
                .header("User-Id", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.userId").value("testUser2"))
            .andExpect(jsonPath("$.tokenStatus").value("ACTIVE"))
    }

    /**
     *  API.3 대기열 순번 조회
     */
    @Test
    fun `대기열 순번 조회에 성공한다`() {
        given(getUserTokenPositionUseCase(any()))
            .willReturn(5L)

        mockMvc.perform(
            get("/ticket-queue-tokens/positions")
                .header("User-Id", "testUser3")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$").value("5"))
    }
}