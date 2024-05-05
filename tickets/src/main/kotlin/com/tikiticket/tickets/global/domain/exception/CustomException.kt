package com.tikiticket.tickets.global.domain.exception

import org.springframework.boot.logging.LogLevel

class CustomException: RuntimeException {
    var logLevel: LogLevel
    var customError: CustomError
    var errorMessage: String

    constructor(logLevel: LogLevel, customError: CustomError) : super(customError.message){
        this.logLevel = logLevel
        this.customError = customError
        this.errorMessage = customError.message
    }

    constructor(logLevel: LogLevel, customError: CustomError, message: String) : super(message){
        this.logLevel = logLevel
        this.customError = customError
        this.errorMessage = message
    }
}