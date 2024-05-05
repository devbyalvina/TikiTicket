package com.tikiticket.tickets.aggregate.ticketqueuetoken.api

import com.tikiticket.tickets.aggregate.ticketqueuetoken.api.dto.CreateTicketQueueTokenResponse
import com.tikiticket.tickets.aggregate.ticketqueuetoken.api.dto.ModifyUserTokenStatusRequest
import com.tikiticket.tickets.aggregate.ticketqueuetoken.api.dto.ModifyUserTokenStatusResponse
import com.tikiticket.tickets.aggregate.ticketqueuetoken.application.CreateTokenUseCase
import com.tikiticket.tickets.aggregate.ticketqueuetoken.application.GetUserTokenPositionUseCase
import com.tikiticket.tickets.aggregate.ticketqueuetoken.application.ModifyUserTokenStatusCommand
import com.tikiticket.tickets.aggregate.ticketqueuetoken.application.ModifyUserTokenStatusUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/ticket-queue-tokens")
class TicketQueueTokenController (
    private val createTokenUseCase: com.tikiticket.tickets.aggregate.ticketqueuetoken.application.CreateTokenUseCase,
    private val modifyUserTokenStatusUseCase: com.tikiticket.tickets.aggregate.ticketqueuetoken.application.ModifyUserTokenStatusUseCase,
    private val getUserTokenPositionUseCase: com.tikiticket.tickets.aggregate.ticketqueuetoken.application.GetUserTokenPositionUseCase
) {
    /**
     *  API.1 대기열 토큰 발급
     */
    @PostMapping
    fun createTicketQueueToken (
        @RequestHeader("User-Id") userId: String
    ): ResponseEntity<com.tikiticket.tickets.aggregate.ticketqueuetoken.api.dto.CreateTicketQueueTokenResponse> {
        val createdToken = createTokenUseCase(userId, LocalDateTime.now())
        val response = com.tikiticket.tickets.aggregate.ticketqueuetoken.api.dto.CreateTicketQueueTokenResponse.of(createdToken)
        return ResponseEntity.ok(response)
    }


    /**
     *  API.2 대기열 토큰 상태 변경
     */
    @PatchMapping("/status")
    fun modifyUserTokenStatus (
        @RequestHeader("User-Id") userId: String,
        @RequestBody requestBody: com.tikiticket.tickets.aggregate.ticketqueuetoken.api.dto.ModifyUserTokenStatusRequest
    ): ResponseEntity<com.tikiticket.tickets.aggregate.ticketqueuetoken.api.dto.ModifyUserTokenStatusResponse> {
        val command = com.tikiticket.tickets.aggregate.ticketqueuetoken.application.ModifyUserTokenStatusCommand(
            userId,
            requestBody.tokenStatus
        )
        val modifiedToken = modifyUserTokenStatusUseCase(command)
        val response = com.tikiticket.tickets.aggregate.ticketqueuetoken.api.dto.ModifyUserTokenStatusResponse.of(modifiedToken)
        return ResponseEntity.ok(response)
    }

    /**
     *  API.3 대기열 순번 조회
     */
    @GetMapping("/positions")
    fun getUserTokenPosition (
        @RequestHeader("User-Id") userId: String,
    ): ResponseEntity<Long> {
        val response = getUserTokenPositionUseCase(userId)
        return ResponseEntity.ok(response)
    }
}
