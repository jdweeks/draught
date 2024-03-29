package io.github.jdweeks.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@Getter
@Setter
@NoArgsConstructor
@PlanningEntity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Player {
    private String name;
    private String position;
    private String team;
    private double fppg;
    private int salary;
    private String gameStartTime;

    @PlanningVariable(valueRangeProviderRefs = "selected")
    private Boolean selected;

    @PlanningId
    protected Integer planningId = this.hashCode();
}
