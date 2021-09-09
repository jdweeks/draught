package io.github.jdweeks.web;

import io.github.jdweeks.domain.Lineup;
import io.github.jdweeks.domain.LineupResponse;
import io.github.jdweeks.service.LineupService;
import lombok.RequiredArgsConstructor;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Path("/lineups")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class LineupController {

    final LineupService lineupService;

    @GET
    public Set<LineupResponse> getLineups() {
        return new HashSet<>();
    }

    @GET
    @Path("/latest")
    public LineupResponse getLatestLineup() {
        return null;
    }

    @POST
    public Response optimizeLineup(final Lineup lineup) {
        CompletableFuture.runAsync(() -> lineupService.optimizeLineup(lineup));
        return Response.accepted().build();
    }
}
