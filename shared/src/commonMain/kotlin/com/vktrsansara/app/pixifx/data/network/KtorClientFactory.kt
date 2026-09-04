package com.vktrsansara.app.pixifx.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object KtorClientFactory {
    fun create(engine: HttpClientEngine? = null): HttpClient {
        val config: io.ktor.client.HttpClientConfig<*>.() -> Unit = {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                        prettyPrint = true
                        encodeDefaults = true
                    }
                )
            }

            install(HttpTimeout) {
                requestTimeoutMillis = 3000
                connectTimeoutMillis = 1500
                socketTimeoutMillis = 3000
            }

            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.INFO
            }
        }

        return if (engine != null) {
            HttpClient(engine, config)
        } else {
            HttpClient(config)
        }
    }
}
