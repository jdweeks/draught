package io.github.jdweeks.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.jdweeks.domain.LineupResponse;
import lombok.SneakyThrows;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class LineupProducer {

    @Inject
    ObjectMapper mapper;

    @Inject
    @Channel("lineups-out")
    Emitter<String> emitter;

    @SneakyThrows
    public void produce(LineupResponse response) {
        emitter.send(mapper.writeValueAsString(response));
    }
}
