package org.example.geomonitoring.output;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Query.query;

@Validated
@RestController
@RequiredArgsConstructor
public class GeoPointController {

    private final MongoTemplate mongoTemplate;
    private final Authorizer authorizer;

    @PostMapping("/points/search")
    @CrossOrigin
    public List<GeoPoint> getPoints(@RequestBody @Valid Filter filter, @RequestHeader String authorization) {
        authorizer.authorize(authorization);
        var criteria = new CriteriaBuilder(filter).build();
        return mongoTemplate.find(query(criteria), GeoPoint.class);
    }
}
