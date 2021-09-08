package io.github.jdweeks.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Lineup {
    private String day;
    private int salaryCap;
    private List<String> rejectList;
}
