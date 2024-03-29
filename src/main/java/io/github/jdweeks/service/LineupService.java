package io.github.jdweeks.service;

import io.github.jdweeks.client.YClient;
import io.github.jdweeks.domain.Lineup;
import io.github.jdweeks.domain.LineupResponse;
import io.github.jdweeks.domain.LineupSolution;
import io.github.jdweeks.domain.Player;
import io.github.jdweeks.kafka.LineupProducer;
import io.github.jdweeks.solver.LineupSolver;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@ApplicationScoped
@RequiredArgsConstructor
public class LineupService {

    final LineupProducer lineupProducer;

    final LineupSolver lineupSolver;

    @Inject
    @RestClient
    YClient yClient;

    // async
    public void optimizeLineup(Lineup lineup) {
        CompletableFuture.runAsync(() -> {
            LineupSolution problem = new LineupSolution();
            problem.setLineup(lineup);

            List<Player> players = yClient.getPlayers().getPlayers().getResult();
            players = players.stream()
                    .filter(p -> p.getFppg() > 0)
                    .filter(p -> p.getGameStartTime().startsWith(lineup.getDay()))
                    .filter(p -> filterExcludedTimes(lineup, p))
                    .filter(p -> !lineup.getRejectList().contains(p.getName()))
                    .collect(Collectors.toList());
            problem.setPlayers(players);

            LineupSolution solution = lineupSolver.solve(problem).await().indefinitely();
            LineupResponse response = buildResponse(solution);

            sendResponse(response);
            saveResponse(response);
        });
    }

    private boolean filterExcludedTimes(Lineup l, Player p) {
        for (String t : l.getExcludeTimes()) {
            if (p.getGameStartTime().contains(t)) return false;
        }
        return true;
    }

    private LineupResponse buildResponse(LineupSolution solution) {
        LineupResponse response = new LineupResponse();

        response.setPlayers(solution.getPlayers().stream().filter(Player::getSelected).collect(Collectors.toList()));
        response.setNumPlayers(response.getPlayers().size());
        response.setTotalPoints(response.getPlayers().stream().mapToDouble(Player::getFppg).sum());
        response.setTotalSalary(response.getPlayers().stream().mapToInt(Player::getSalary).sum());

        return response;
    }

    private void sendResponse(LineupResponse response) { lineupProducer.produce(response); }

    private void saveResponse(LineupResponse response) { response.persist(); }
}
