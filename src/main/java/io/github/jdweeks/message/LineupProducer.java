package io.github.jdweeks.message;

import io.github.jdweeks.domain.LineupResponse;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class LineupProducer {

    @Inject
    @Channel("lineups-out")
    Emitter<Message<LineupResponse>> emitter;

    public void produce(LineupResponse response) {
        emitter.send(Message.of(response));
    }
}
