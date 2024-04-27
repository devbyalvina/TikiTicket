package com.tikiticket.tickets.concert.application

import com.tikiticket.tickets.appcore.application.exception.CustomException
import com.tikiticket.tickets.appcore.application.log.LogLevel
import com.tikiticket.tickets.concert.domain.Concert
import com.tikiticket.tickets.concert.domain.ConcertError

class ConcertValidator {
    /**
     *  좌석 존재 여부 체크
     */
    companion object {
        fun checkSeatsExist(concert: Concert) {
            require(!concert.seats.isNullOrEmpty()) {
                throw CustomException(LogLevel.WARN, ConcertError.CONCERT_SEATS_NOT_FOUND)
            }
        }
    }
}