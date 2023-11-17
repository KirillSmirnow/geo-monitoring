package org.example.geomonitoring.output;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(of = "id")
public class GeoPoint {
    private final UUID id;
    private final String objectId;
    private final LocalDateTime utcDateTime;
    private final double longitude;
    private final double latitude;
}
