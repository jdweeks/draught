package io.github.jdweeks.domain;

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LineupResponse extends ReactivePanacheMongoEntity {
    private int numPlayers;
    private int totalSalary;
    private double totalPoints;
    private List<Player> players;
}
