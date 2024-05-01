package com.tikiticket.tickets.balance.api

import com.tikiticket.tickets.balance.api.dto.GetBalanceResponse
import com.tikiticket.tickets.balance.application.ChangeBalanceUseCase
import com.tikiticket.tickets.balance.application.GetBalanceUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/balances")
class BalanceController (
    private val getBalanceUseCase: GetBalanceUseCase,
    private val changeBalanceUseCase: ChangeBalanceUseCase
){
    /**
     *  API.9 잔고조회
     */
    @GetMapping
    fun getBalance (
        @RequestHeader("User-Id") userId: String
    ): ResponseEntity<GetBalanceResponse> {
        val retrievedBalance = getBalanceUseCase(userId)
        val response = GetBalanceResponse.of(retrievedBalance)
        return ResponseEntity.ok(response)
    }
}