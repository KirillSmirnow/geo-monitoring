package org.example.geomonitoring.output;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static java.util.UUID.randomUUID;

@Slf4j
@Component
public class Authorizer {

    private static final String token = randomUUID().toString();

    @EventListener(ApplicationReadyEvent.class)
    public void printToken() {
        log.info("Access token: {}", token);
    }

    public void authorize(String authorization) {
        if (!token.equals(authorization)) {
            log.warn("Authorization failed for value {}", authorization);
            throw new RuntimeException();
        }
    }
}
