package io.github.jdweeks.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.jdweeks.domain.Lineup;
import io.github.jdweeks.service.LineupService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@RequiredArgsConstructor
public class LineupConsumer {

    final ObjectMapper mapper;

    final LineupService lineupService;

    @SneakyThrows
    @Incoming("lineups-in")
    @Acknowledgment(Acknowledgment.Strategy.NONE)
    public void consume(final String lineupStr) {
        lineupService.optimizeLineup(mapper.readValue(lineupStr, Lineup.class));
    }
}
