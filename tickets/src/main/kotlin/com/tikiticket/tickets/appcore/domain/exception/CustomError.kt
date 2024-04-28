package com.tikiticket.tickets.appcore.domain.exception

interface CustomError {
    val errorCode: String
    val message: String
}
