package io.github.jdweeks.web;

import io.github.jdweeks.domain.Lineup;
import io.github.jdweeks.service.LineupService;
import lombok.RequiredArgsConstructor;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.CompletableFuture;

@Path("/lineup")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class LineupController {

    final LineupService lineupService;

    @POST
    public Response optimizeLineup(final Lineup lineup) {
        CompletableFuture.runAsync(() -> lineupService.optimizeLineup(lineup));
        return Response.accepted().build();
    }
}
