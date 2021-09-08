package io.github.jdweeks.kafka;

import io.github.jdweeks.domain.LineupResponse;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class LineupProducer {

    @Inject
    @Channel("lineups-out")
    Emitter<LineupResponse> emitter;

    public void produce(LineupResponse response) {
        emitter.send(response);
    }
}
