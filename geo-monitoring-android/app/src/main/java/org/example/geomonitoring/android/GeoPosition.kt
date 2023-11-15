package org.example.geomonitoring.android

import kotlinx.serialization.Serializable
import org.example.geomonitoring.android.serializer.LocalDateTimeSerializer
import org.example.geomonitoring.android.serializer.UuidSerializer
import java.time.LocalDateTime
import java.util.UUID

@Serializable
data class GeoPosition(
    @Serializable(with = UuidSerializer::class)
    val id: UUID,
    val objectId: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    val utcDateTime: LocalDateTime,
    val latitude: Double,
    val longitude: Double,
)
