package org.example.geomonitoring.input;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@EqualsAndHashCode(of = "id")
public class GeoPoint {

    @NotNull
    private final UUID id;

    @NotBlank
    private final String objectId;

    @NotNull
    private final LocalDateTime utcDateTime;

    @Min(-180)
    @Max(180)
    private final double longitude;

    @Min(-90)
    @Max(90)
    private final double latitude;
}
