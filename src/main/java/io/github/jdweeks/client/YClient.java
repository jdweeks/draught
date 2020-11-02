package io.github.jdweeks.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/v2")
@RegisterRestClient
public interface YClient {

    @GET
    @Path("/external/playersFeed/nfl")
    @Produces("application/json")
    YResponse getPlayers();
}
