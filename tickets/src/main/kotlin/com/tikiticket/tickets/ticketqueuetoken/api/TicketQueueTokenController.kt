package com.tikiticket.tickets.ticketqueuetoken.api

import com.tikiticket.tickets.ticketqueuetoken.api.dto.CreateTicketQueueTokenResponse
import com.tikiticket.tickets.ticketqueuetoken.api.dto.ModifyUserTokenStatusRequest
import com.tikiticket.tickets.ticketqueuetoken.api.dto.ModifyUserTokenStatusResponse
import com.tikiticket.tickets.ticketqueuetoken.application.CreateTokenUseCase
import com.tikiticket.tickets.ticketqueuetoken.application.GetUserTokenPositionUseCase
import com.tikiticket.tickets.ticketqueuetoken.application.ModifyUserTokenStatusCommand
import com.tikiticket.tickets.ticketqueuetoken.application.ModifyUserTokenStatusUseCase
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


    /**
     *  API.2 대기열 토큰 상태 변경
     */
    @PatchMapping("/status")
    fun modifyUserTokenStatus (
        @RequestHeader("User-Id") userId: String,
        @RequestBody requestBody: ModifyUserTokenStatusRequest
    ): ResponseEntity<ModifyUserTokenStatusResponse> {
        val command = ModifyUserTokenStatusCommand(userId, requestBody.tokenStatus)
        val modifiedToken = modifyUserTokenStatusUseCase(command)
        val response = ModifyUserTokenStatusResponse.of(modifiedToken)
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
