package io.github.jdweeks.client;

import io.github.jdweeks.domain.Player;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class YResponse {

    private PlayersContainer players;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class PlayersContainer {
        List<Player> result;
    }
}
