package io.github.jdweeks.solver;

import io.github.jdweeks.domain.Lineup;
import io.github.jdweeks.domain.Player;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintCollectors;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;

public class LineupConstraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(final ConstraintFactory constraintFactory) {
        return new Constraint[] {
            salaryCap(constraintFactory),
            maxPoints(constraintFactory),
            acceptList(constraintFactory),
            rosterFormat(constraintFactory)
        };
    }

    private Constraint maxPoints(final ConstraintFactory constraintFactory) {
        return constraintFactory.from(Player.class)
                .filter(Player::getSelected)
                .reward("Max FPPG", HardSoftScore.ONE_SOFT, p -> (int)p.getFppg());
    }

    private Constraint salaryCap(final ConstraintFactory constraintFactory) {
        return constraintFactory.from(Player.class)
                .filter(Player::getSelected)
                .groupBy(ConstraintCollectors.sum(Player::getSalary))
                .join(Lineup.class)
                .filter((s, l) -> s > l.getSalaryCap())
                .penalize("Salary Cap", HardSoftScore.ONE_HARD, (s, l) -> s - l.getSalaryCap());
    }

    private Constraint acceptList(final ConstraintFactory constraintFactory) {
        return constraintFactory.from(Player.class)
                .filter(Player::getSelected)
                .join(Lineup.class)
                .filter((p, l) -> l.getAcceptList().contains(p.getName()))
                .reward("Accept List", HardSoftScore.ONE_HARD, (s, l) -> s.getSalary());
    }

    private Constraint rosterFormat(final ConstraintFactory constraintFactory) {
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
