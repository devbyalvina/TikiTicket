package com.tikiticket.tickets.concert.application

import com.tikiticket.tickets.concert.application.exception.ConcertError
import com.tikiticket.tickets.concert.application.exception.ConcertException
import com.tikiticket.tickets.concert.domain.Concert
import com.tikiticket.tickets.concert.domain.ConcertService
import org.springframework.stereotype.Component

/**
 *  API.4 콘서트 스케줄 목록 조회
 */
@Component
class GetConcertsByDateRangeUseCase (
    private val concertService: ConcertService
) {
    operator fun invoke(command: GetConcertsByDateRangeCommand): List<Concert> {
        command.validate()

        val concerts = concertService.findConcertsByDateRange(command.startDate, command.endDate)
            ?: throw ConcertException(ConcertError.CONCERT_NOT_FOUND)

        return concerts
    }
}