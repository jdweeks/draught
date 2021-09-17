package io.github.jdweeks.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Lineup {
    private int salaryCap = 200;
    private String day = "Sun";
    private List<String> excludeTimes;
    private List<String> acceptList;
    private List<String> rejectList;
}
