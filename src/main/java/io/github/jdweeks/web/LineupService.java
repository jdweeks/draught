package io.github.jdweeks.web;

import io.github.jdweeks.client.YClient;
import io.github.jdweeks.domain.Lineup;
import io.github.jdweeks.domain.LineupSolution;
import io.github.jdweeks.domain.Player;
import io.github.jdweeks.solver.LineupSolver;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class LineupService {

    @Inject
    LineupSolver lineupSolver;

    @Inject
    @RestClient
    YClient yClient;

    public LineupResponse getLineup() {
        List<Player> players = yClient.getPlayers().getPlayers().getResult();
        LineupSolution problem = new LineupSolution();
        Lineup lineup = new Lineup();

        lineup.setSalaryCap(200);
        problem.setLineup(lineup);

        String[] moved = {};
        String[] out = {};
	String day = "Sun";

        players = players.stream()
                .filter(p -> p.getGameStartTime().startsWith(day))
                .filter(p -> p.getFppg() > 1)
                .filter(p -> !Arrays.asList(moved).contains(p.getTeam()))
                .filter(p -> !Arrays.asList(out).contains(p.getName()))
                .collect(Collectors.toList());

        problem.setPlayers(players);

        return buildResponse(lineupSolver.solve(problem));
    }

    private LineupResponse buildResponse(LineupSolution solution) {
        LineupResponse response = new LineupResponse();

        response.setPlayers(solution.getPlayers().stream().filter(Player::getSelected).collect(Collectors.toList()));
        response.setNumPlayers(response.getPlayers().size());
        response.setTotalPoints(response.getPlayers().stream().mapToDouble(Player::getFppg).sum());
        response.setTotalSalary(response.getPlayers().stream().mapToInt(Player::getSalary).sum());

        return response;
    }
}
