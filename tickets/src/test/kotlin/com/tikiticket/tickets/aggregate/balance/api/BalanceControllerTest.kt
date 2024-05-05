package com.tikiticket.tickets.aggregate.balance.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.tikiticket.tickets.aggregate.balance.api.BalanceController
import com.tikiticket.tickets.aggregate.balance.api.dto.ChangeBalanceRequest
import com.tikiticket.tickets.aggregate.balance.application.ChangeBalanceUseCase
import com.tikiticket.tickets.aggregate.balance.application.GetBalanceUseCase
import com.tikiticket.tickets.aggregate.balance.domain.Balance
import com.tikiticket.tickets.aggregate.balance.domain.TransactionType
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDateTime

@WebMvcTest(BalanceController::class)
class BalanceControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @MockBean
    lateinit var getBalanceUseCase: GetBalanceUseCase

    @MockBean
    lateinit var changeBalanceUseCase: ChangeBalanceUseCase

    /**
     *   API.9 잔고 조회
     */
    @Test
    fun `잔고조회를 성공한다`() {
        val userId = "testUser1"
        val createdTime = LocalDateTime.of(2024, 5, 2, 6, 0, 0)
        val currentTime = LocalDateTime.of(2024, 5, 2, 6, 30, 0)

        val balance = Balance (
            1L,
            userId,
            50000L,
            createdTime,
            currentTime,
        )

        given(getBalanceUseCase(userId))
            .willReturn(balance)

        mockMvc.perform(
            get("/balances")
                .header("User-Id", userId)
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value("testUser1"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.balanceAmount").value("50000"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.updatedAt").value("2024-05-02T06:30:00"))
    }

    /**
     *   API.10 잔고 변경
     */
    @Test
    fun `잔고변경을 성공한다`() {
        val userId = "testUser2"
        val createdTime = LocalDateTime.of(2024, 5, 2, 6, 0, 0)
        val currentTime = LocalDateTime.of(2024, 5, 2, 7, 50, 0)

        val balance = Balance (
            1L,
            userId,
            70000L,
            createdTime,
            currentTime
        )

        given(changeBalanceUseCase(any()))
            .willReturn(balance)

        val request = ChangeBalanceRequest(TransactionType.CHARGE, 20000L)

        mockMvc.perform(
            patch("/balances")
                .header("User-Id", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value("testUser2"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.balanceAmount").value("70000"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.updatedAt").value("2024-05-02T07:50:00"))
    }
}