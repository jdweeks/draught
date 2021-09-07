package io.github.jdweeks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LineupResponse {

    private int numPlayers;
    private int totalSalary;
    private double totalPoints;
    private List<Player> players;

}