package io.github.jdweeks.solver;

import io.github.jdweeks.domain.LineupSolution;
import org.optaplanner.core.api.solver.SolverJob;
import org.optaplanner.core.api.solver.SolverManager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@ApplicationScoped
public class LineupSolver {

    @Inject
    SolverManager<LineupSolution, UUID> solverManager;

    public LineupSolution solve(LineupSolution problem) {
        SolverJob<LineupSolution, UUID> solverJob = solverManager.solve(UUID.randomUUID(), problem);

        LineupSolution solution;
        try {
            // Wait until the solving ends
            solution = solverJob.getFinalBestSolution();
        } catch (InterruptedException | ExecutionException e) {
            throw new IllegalStateException("Solving failed.", e);
        }

        return solution;
    }
}
