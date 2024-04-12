package com.tikiticket.tickets.concert.application

import com.tikiticket.tickets.concert.application.exception.ConcertError
import com.tikiticket.tickets.concert.application.exception.ConcertException
import java.time.LocalDate

data class GetConcertsByDateRangeCommand (
    val startDate: LocalDate,
    val endDate: LocalDate
) {
    fun validate() {
        checkStartDateBeforeEndDate()
    }

    /**
     *  startDate가 endDate보다 이전인지 체크
     */
    fun checkStartDateBeforeEndDate() {
        if (startDate > endDate) {
            throw ConcertException(ConcertError.INVALID_DATE_RANGE_PARAMETER)
        }
    }
}