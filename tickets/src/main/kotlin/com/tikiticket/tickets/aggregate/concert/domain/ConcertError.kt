package com.tikiticket.tickets.aggregate.concert.domain

import com.tikiticket.tickets.global.domain.exception.CustomError

enum class ConcertError (
    override val message: String,
): CustomError {
    /**
     *  INVALID_DATE_RANGE_PARAMETER
     *  - API.4 콘서트 스케줄 목록 조회
     */
    INVALID_DATE_RANGE_PARAMETER("Invalid date range. Please specify either start_date and end_date or date_range."),
    /**
     *  CONCERT_NOT_FOUND
     *  - API.4 콘서트 스케줄 목록 조회
     *  - API.5 콘서트 좌석 목록 조회
     */
    CONCERT_NOT_FOUND("The specified concert was not found."),
    /**
     *  CONCERT_SEATS_NOT_FOUND
     *  - API.5 콘서트 좌석 목록 조회
     */
    CONCERT_SEATS_NOT_FOUND("Seats for the specified concert were not found."),
    /**
     *  CONCERT_SEAT_NOT_FOUND
     *  - API.6 예매
     */
    CONCERT_SEAT_NOT_FOUND("The specified seat was not found."),
    /**
     *  SEAT_NOT_AVAILABLE
     *  - API.6 예매
     */
    SEAT_NOT_AVAILABLE("The selected seat can not be booked.");

    override val errorCode: String = name
}