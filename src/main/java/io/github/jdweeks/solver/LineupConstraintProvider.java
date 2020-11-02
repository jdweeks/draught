package io.github.jdweeks.solver;

import io.github.jdweeks.domain.Lineup;
import io.github.jdweeks.domain.Player;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.*;

public class LineupConstraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[] {
            salaryCap(constraintFactory),
            maxPoints(constraintFactory),
            rosterFormat(constraintFactory)
        };
    }

    private Constraint maxPoints(ConstraintFactory constraintFactory) {
        return constraintFactory.from(Player.class)
                .filter(Player::getSelected)
                .reward("Max FPPG", HardSoftScore.ONE_SOFT, p -> (int)p.getFppg());
    }

    private Constraint salaryCap(ConstraintFactory constraintFactory) {
        return constraintFactory.from(Player.class)
                .filter(Player::getSelected)
                .groupBy(ConstraintCollectors.sum(Player::getSalary))
                .join(Lineup.class)
                .filter((s, l) -> s > l.getSalaryCap())
                .penalize("Salary Cap", HardSoftScore.ONE_HARD, (s, l) -> s - l.getSalaryCap());
    }

    private Constraint rosterFormat(ConstraintFactory constraintFactory) {
        return constraintFactory.from(Player.class)
                .filter(Player::getSelected)
                .groupBy(Player::getPosition, ConstraintCollectors.count())
                .filter((p, c) -> {
                    switch(p) {
                        case "QB":
                        case "TE":
                        case "DEF":
                            return c > 1;
                        case "RB":
                        case "WR":
                            return c > 3;
                        default:
                            return c > 0;
                    }
                })
                .penalize("Roster Format", HardSoftScore.ONE_HARD, (p, c) -> c);
    }
}
