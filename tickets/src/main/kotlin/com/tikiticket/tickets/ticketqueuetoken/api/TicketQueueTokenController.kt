package com.tikiticket.tickets.ticketqueuetoken.api

import com.tikiticket.tickets.ticketqueuetoken.application.CreateTokenUseCase
import com.tikiticket.tickets.ticketqueuetoken.application.GetUserTokenPositionUseCase
import com.tikiticket.tickets.ticketqueuetoken.application.ModifyUserTokenStatusUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/ticket-queue-tokens")
class TicketQueueTokenController (
    private val createTokenUseCase: CreateTokenUseCase,
    private val modifyUserTokenStatusUseCase: ModifyUserTokenStatusUseCase,
    private val getUserTokenPositionUseCase: GetUserTokenPositionUseCase
) {
    /**
     *  API.1 대기열 토큰 발급
     */
    @PostMapping
    fun createTicketQueueToken (
        @RequestHeader("User-Id") userId: String
    ): ResponseEntity<CreateTicketQueueTokenResponse> {
        val createdToken = createTokenUseCase(userId, LocalDateTime.now())
        val response = CreateTicketQueueTokenResponse.of(createdToken)
        return ResponseEntity.ok(response)
    }
}
