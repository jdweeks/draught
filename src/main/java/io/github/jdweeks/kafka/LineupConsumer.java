package io.github.jdweeks.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.jdweeks.domain.Lineup;
import io.github.jdweeks.service.LineupService;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.CompletableFuture;

@ApplicationScoped
@RequiredArgsConstructor
public class LineupConsumer {

    final ObjectMapper mapper;

    final LineupService lineupService;

    @Incoming("lineups-in")
    @Acknowledgment(Acknowledgment.Strategy.NONE)
    public void consume(final String lineupStr) {
        CompletableFuture.runAsync(() -> {
            try {
                lineupService.optimizeLineup(mapper.readValue(lineupStr, Lineup.class));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }
}
