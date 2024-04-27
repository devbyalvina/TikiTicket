package com.tikiticket.tickets.appcore.application.exception

interface CustomError {
    val errorCode: String
    val message: String
}
