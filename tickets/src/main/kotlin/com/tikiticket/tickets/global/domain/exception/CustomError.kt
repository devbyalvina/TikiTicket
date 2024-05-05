package com.tikiticket.tickets.global.domain.exception

interface CustomError {
    val errorCode: String
    val message: String
}
