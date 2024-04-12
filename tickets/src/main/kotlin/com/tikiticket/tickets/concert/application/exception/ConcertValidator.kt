package com.tikiticket.tickets.concert.application.exception

import com.tikiticket.tickets.concert.domain.Concert

class ConcertValidator {
    /**
     *  좌석 존재 여부 체크
     */
    companion object {
        fun checkSeatsExist(concert: Concert) {
            if (concert.seats.isNullOrEmpty()) {
                throw ConcertException(ConcertError.CONCERT_SEATS_NOT_FOUND)
            }
        }
    }
}