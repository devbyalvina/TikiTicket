package com.tikiticket.tickets.ticketqueuetoken.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.tikiticket.tickets.ticketqueuetoken.application.CreateTokenUseCase
import com.tikiticket.tickets.ticketqueuetoken.application.GetUserTokenPositionUseCase
import com.tikiticket.tickets.ticketqueuetoken.application.ModifyUserTokenStatusUseCase
import com.tikiticket.tickets.ticketqueuetoken.domain.TicketQueueToken
import com.tikiticket.tickets.ticketqueuetoken.domain.TokenStatusType
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime

@WebMvcTest(TicketQueueTokenController::class)
class TicketQueueTokenControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @MockBean
    lateinit var createTokenUseCase: CreateTokenUseCase

    @MockBean
    lateinit var modifyUserTokenStatusUseCase: ModifyUserTokenStatusUseCase

    @MockBean
    lateinit var getUserTokenPositionUseCase: GetUserTokenPositionUseCase

    /**
     *  API.1 대기열 토큰 발급
     */
    @Test
    fun `대기열 토큰 발급에 성공한다`() {
        val userId = "testUser"
        val currentTime = LocalDateTime.of(2024, 5, 2, 4, 0, 0)

        val ticketQueueToken = TicketQueueToken (
            1,
            userId,
            TokenStatusType.WAITING,
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

        val ticketQueueToken = TicketQueueToken (
            1,
            userId,
            TokenStatusType.ACTIVE,
            createdTime.plusMinutes(5),
            createdTime,
            modifiedTime
        )

        given(modifyUserTokenStatusUseCase(any()))
            .willReturn(ticketQueueToken)

        val request = ModifyUserTokenStatusRequest(TokenStatusType.ACTIVE)

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
}