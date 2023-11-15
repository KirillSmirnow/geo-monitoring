package org.example.geomonitoring.android

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object Client {

    private const val serverBaseUrl = "http://164.90.184.120:8080"

    fun send(geoPosition: GeoPosition) {
        val response = khttp.put(
            url = "$serverBaseUrl/register",
            data = Json.encodeToString(setOf(geoPosition)),
            headers = mapOf(Pair("Content-Type", "application/json")),
        )
        if (response.statusCode != 200) {
            throw RuntimeException(response.text)
        }
    }
}
