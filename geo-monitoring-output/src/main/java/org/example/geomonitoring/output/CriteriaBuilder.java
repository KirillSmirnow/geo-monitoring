package org.example.geomonitoring.output;

import lombok.Data;
import org.springframework.data.mongodb.core.query.Criteria;

@Data
public class CriteriaBuilder {

    private static final Criteria TRUE = new Criteria();

    private final Filter filter;

    public Criteria build() {
        return new Criteria().andOperator(
                filterByObjectId(),
                filterByDateTime(),
                filterByPoint()
        );
    }

    private Criteria filterByObjectId() {
        var objectIds = filter.getObjectIds();
        if (objectIds == null || objectIds.isEmpty()) {
            return TRUE;
        }
        return Criteria.where("objectId").in(objectIds);
    }

    private Criteria filterByDateTime() {
        var range = filter.getUtcDateTimeRange();
        if (range == null) {
            return TRUE;
        }
        return Criteria.where("utcDateTime")
                .gte(range.getFrom())
                .lte(range.getTo());
    }

    private Criteria filterByPoint() {
        var range = filter.getPointRange();
        if (range == null) {
            return TRUE;
        }
        return Criteria.where("longitude")
                .gte(range.getFrom().getLongitude())
                .lte(range.getTo().getLongitude())
                .and("latitude")
                .gte(range.getFrom().getLatitude())
                .lte(range.getTo().getLatitude());
    }
}
