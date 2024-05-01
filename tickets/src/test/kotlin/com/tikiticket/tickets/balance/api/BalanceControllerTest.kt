package com.tikiticket.tickets.balance.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.tikiticket.tickets.balance.application.ChangeBalanceUseCase
import com.tikiticket.tickets.balance.application.GetBalanceUseCase
import com.tikiticket.tickets.balance.domain.Balance
import org.junit.jupiter.api.Test
import org.mockito.kotlin.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
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
     *   API.9 잔고조회
     */
    @Test
    fun `잔고조회를 성공한다`() {
        val userId = "testUser1"
        val createdTime = LocalDateTime.of(2024, 5, 2, 6, 0, 0)
        val currentTime = LocalDateTime.of(2024, 5, 2, 6, 30, 0)

        val balance = Balance (
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
}