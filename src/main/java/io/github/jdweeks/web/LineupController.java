package io.github.jdweeks.web;

import io.github.jdweeks.domain.Lineup;
import io.github.jdweeks.domain.LineupResponse;
import io.github.jdweeks.service.LineupService;
import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/lineups")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class LineupController {

    final LineupService lineupService;

    private final static String QUERY_LATEST = "{ sort: { _id: -1 }, limit: 1 }";

    @GET
    public Uni<List<LineupResponse>> getLineups() {
        return LineupResponse.listAll();
    }

    @GET
    @Path("/latest")
    public Uni<List<LineupResponse>> getLatest() {
        return LineupResponse.list(QUERY_LATEST);
    }

    @POST
    public Response optimizeLineup(final Lineup lineup) {
        lineupService.optimizeLineup(lineup); // async
        return Response.accepted().build();
    }
}
