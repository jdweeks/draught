package io.github.jdweeks.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Lineup {
    private String day = "Sun";
    private int salaryCap = 200;
    private List<String> acceptList;
    private List<String> rejectList;
}
