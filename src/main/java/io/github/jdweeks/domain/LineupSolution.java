package io.github.jdweeks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactProperty;
import org.optaplanner.core.api.domain.valuerange.CountableValueRange;
import org.optaplanner.core.api.domain.valuerange.ValueRangeFactory;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@PlanningSolution
public class LineupSolution {

    @PlanningEntityCollectionProperty
    private List<Player> players;

    @ProblemFactProperty
    private Lineup lineup;

    @ValueRangeProvider(id = "selected")
    public CountableValueRange<Boolean> getSelected() {
        return ValueRangeFactory.createBooleanValueRange();
    }

    @PlanningScore
    private HardSoftScore score;
}
