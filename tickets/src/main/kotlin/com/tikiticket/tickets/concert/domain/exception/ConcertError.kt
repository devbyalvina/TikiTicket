package com.tikiticket.tickets.concert.domain.exception

enum class ConcertError (
    val code: String,
    val message: String,
) {
    // API.4 콘서트 스케줄 목록 조회
    CONCERT_NOT_FOUND("CONCERT_NOT_FOUND", "The specified concert was not found."),
}