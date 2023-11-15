package org.example.geomonitoring.input;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@Validated
@RestController
@RequiredArgsConstructor
public class GeoPointController {

    private final GeoPointRepository geoPointRepository;

    @PutMapping("/register")
    public void registerGeoPoints(@RequestBody Set<@Valid GeoPoint> geoPoints) {
        geoPointRepository.saveAll(geoPoints);
    }
}
