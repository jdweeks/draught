package io.github.jdweeks.kafka;

import io.github.jdweeks.domain.Lineup;
import io.github.jdweeks.service.LineupService;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class LineupConsumer {

    @Inject
    LineupService lineupService;

    @Incoming("lineups-in")
    public CompletionStage<Void> consume(Message<Lineup> lineupMsg) {
        CompletableFuture.runAsync(() -> lineupService.optimizeLineup(lineupMsg.getPayload()));
        return lineupMsg.ack();
    }
}
