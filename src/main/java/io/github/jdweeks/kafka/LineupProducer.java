package io.github.jdweeks.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.jdweeks.domain.LineupResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
@RequiredArgsConstructor
public class LineupProducer {

    final ObjectMapper mapper;

    @Channel("lineups-out")
    Emitter<String> emitter;

    @SneakyThrows
    public CompletionStage<Void> produce(final LineupResponse response) {
        return emitter.send(mapper.writeValueAsString(response));
    }
}
