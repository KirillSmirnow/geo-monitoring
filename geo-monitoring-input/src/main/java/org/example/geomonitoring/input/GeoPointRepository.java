package org.example.geomonitoring.input;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface GeoPointRepository extends MongoRepository<GeoPoint, UUID> {
}
