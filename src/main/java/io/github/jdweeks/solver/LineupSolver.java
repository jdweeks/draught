package io.github.jdweeks.solver;

import io.github.jdweeks.domain.LineupSolution;
import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;
import org.optaplanner.core.api.solver.SolverJob;
import org.optaplanner.core.api.solver.SolverManager;

import javax.enterprise.context.ApplicationScoped;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@ApplicationScoped
@RequiredArgsConstructor
public class LineupSolver {

    final SolverManager<LineupSolution, UUID> solverManager;

    public Uni<LineupSolution> solve(final LineupSolution problem) {
        SolverJob<LineupSolution, UUID> solverJob = solverManager.solve(UUID.randomUUID(), problem);

        LineupSolution solution;
        try {
            // Wait until the solving ends
            solution = solverJob.getFinalBestSolution();
        } catch (InterruptedException | ExecutionException e) {
            return Uni.createFrom().failure(new IllegalStateException("Solving failed.", e));
        }

        return Uni.createFrom().item(solution);
    }
}
