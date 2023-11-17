package org.example.geomonitoring.output;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class Filter {

    private final Set<@NotBlank String> objectIds;

    @Valid
    private final Range<LocalDateTime> utcDateTimeRange;

    @Valid
    private final Range<Point> pointRange;

    @Data
    public static class Range<T> {

        @NotNull
        @Valid
        private final T from;

        @NotNull
        @Valid
        private final T to;
    }

    @Data
    public static class Point {

        @Min(-180)
        @Max(180)
        private final double longitude;

        @Min(-90)
        @Max(90)
        private final double latitude;
    }
}
