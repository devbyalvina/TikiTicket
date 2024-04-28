package com.tikiticket.tickets.appcore.domain.log

enum class LogLevel {
    /**
     * INFO: 정보성 메세지
     * WARN: 애플리케이션의 실행에는 영향을 미치지 않는 문제에 대한 경고성 메시지
     * ERROR: 심각한 문제가 발생했음을 알리는 에러 메시지
     */
    INFO, WARN, ERROR
}