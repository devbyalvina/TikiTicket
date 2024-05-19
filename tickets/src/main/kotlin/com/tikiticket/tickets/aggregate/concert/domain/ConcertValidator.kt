package com.tikiticket.tickets.aggregate.concert.domain

import com.tikiticket.tickets.global.domain.exception.CustomException
import org.springframework.boot.logging.LogLevel

class ConcertValidator {
    /**
     *  좌석 존재 여부 체크
     */
    companion object {
        fun checkSeatsExist(concert: Concert) {
            require(!concert.seats?.list.isNullOrEmpty()) {
                throw CustomException(LogLevel.WARN, ConcertError.CONCERT_SEATS_NOT_FOUND)
            }
        }
    }
}