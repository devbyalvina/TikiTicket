package com.tikiticket.tickets.concert.domain.exception

enum class ConcertError (
    val code: String,
    val message: String,
) {
    // API.4 콘서트 스케줄 목록 조회
    // API.5 콘서트 좌석 목록 조회
    CONCERT_NOT_FOUND("CONCERT_NOT_FOUND", "The specified concert was not found."),
    // API.5 콘서트 좌석 목록 조회
    CONCERT_SEATS_NOT_FOUND("CONCERT_SEATS_NOT_FOUND", "Seats for the specified concert were not found.")
}