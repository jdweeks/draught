package io.github.jdweeks.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.jdweeks.domain.Lineup;
import io.github.jdweeks.service.LineupService;
import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;

@ApplicationScoped
public class LineupConsumer {

    @Inject
    ObjectMapper mapper;

    @Inject
    LineupService lineupService;

    @Incoming("lineups-in")
    @Acknowledgment(Acknowledgment.Strategy.NONE)
    public void consume(String lineupStr) {
        CompletableFuture.runAsync(() -> {
            try {
                lineupService.optimizeLineup(mapper.readValue(lineupStr, Lineup.class));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }
}
