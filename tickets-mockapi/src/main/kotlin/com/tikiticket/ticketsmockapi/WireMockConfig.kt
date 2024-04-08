package com.tikiticket.ticketsmockapi

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.github.tomakehurst.wiremock.stubbing.StubMapping
import org.json.JSONObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.io.Resource
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.util.StreamUtils


@Configuration
@PropertySource("application.yml")
class WireMockConfig {
    private val log: Logger = LoggerFactory.getLogger(WireMockConfig::class.java)

    @Value("\${wiremock.server.port}")
    private lateinit var port: String

    @Bean(initMethod = "start", destroyMethod = "stop")
    fun wireMockServer(): WireMockServer {
        log.info("[WireMockConfig] start")
        val wireMockServer = WireMockServer(WireMockConfiguration.options().disableRequestJournal().port(port.toInt()))
        val resolver = PathMatchingResourcePatternResolver()
        val resources: Array<Resource> = resolver.getResources("classpath:/mappings/mock/**/*.json")

        for (resource in resources) {
            log.info("[WireMockConfig] jsonFilePath : {}", resource.url.path)
            val resourceString = readJsonFile(resource)
            val stubMappings = convertResourceStringToStubMappings(resourceString)
            stubMappings.forEach() {
                wireMockServer.addStubMapping(StubMapping.buildFrom(it))
            }
        }
        log.info("[WireMockConfig] finish")
        return wireMockServer
    }

    private fun readJsonFile(resource: Resource): String {
        return StreamUtils.copyToString(resource.inputStream, Charsets.UTF_8)
    }

    private fun convertResourceStringToStubMappings(resourceString: String): List<String> {
        val jsonResource = JSONObject(resourceString)
        val jsonArrayStubMappings = jsonResource.optJSONArray("mappings")
        val stubMappings: List<String> = (0 until jsonArrayStubMappings.length()).map {
            jsonArrayStubMappings.get(it).toString()
        }
        return stubMappings;
    }
}