package io.github.jdweeks.web;

import io.github.jdweeks.domain.LineupSolution;
import io.github.jdweeks.domain.Lineup;
import io.github.jdweeks.domain.Player;
import org.optaplanner.core.api.solver.SolverJob;
import org.optaplanner.core.api.solver.SolverManager;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Path("/lineup")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LineupResource {

    @Inject
    LineupService lineupService;

    @POST
    public LineupResponse optimizeLineup(Lineup lineup) {
        return lineupService.optimizeLineup(lineup);
    }
}
