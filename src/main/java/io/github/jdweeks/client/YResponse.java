package io.github.jdweeks.client;

import io.github.jdweeks.domain.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class YResponse {

    private PlayersContainer players;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlayersContainer {
        List<Player> result;
    }
}
