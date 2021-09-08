package io.github.jdweeks.service;

import io.github.jdweeks.client.YClient;
import io.github.jdweeks.domain.Lineup;
import io.github.jdweeks.domain.LineupResponse;
import io.github.jdweeks.domain.LineupSolution;
import io.github.jdweeks.domain.Player;
import io.github.jdweeks.kafka.LineupProducer;
import io.github.jdweeks.solver.LineupSolver;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class LineupService {

    @Inject
    LineupProducer lineupProducer;

    @Inject
    LineupSolver lineupSolver;

    @Inject
    @RestClient
    YClient yClient;

    public void optimizeLineup(Lineup lineup) {
        List<Player> players = yClient.getPlayers().getPlayers().getResult();
        LineupSolution problem = new LineupSolution();
        problem.setLineup(lineup);

        players = players.stream()
                .filter(p -> p.getGameStartTime().startsWith(lineup.getDay()))
                .filter(p -> !lineup.getRejectList().contains(p.getName()))
                .collect(Collectors.toList());
        problem.setPlayers(players);

        sendResponse(buildResponse(lineupSolver.solve(problem)));
    }

    private LineupResponse buildResponse(LineupSolution solution) {
        LineupResponse response = new LineupResponse();

        response.setPlayers(solution.getPlayers().stream().filter(Player::getSelected).collect(Collectors.toList()));
        response.setNumPlayers(response.getPlayers().size());
        response.setTotalPoints(response.getPlayers().stream().mapToDouble(Player::getFppg).sum());
        response.setTotalSalary(response.getPlayers().stream().mapToInt(Player::getSalary).sum());

        return response;
    }

    private void sendResponse(LineupResponse response) {
        lineupProducer.produce(response);
    }
}
