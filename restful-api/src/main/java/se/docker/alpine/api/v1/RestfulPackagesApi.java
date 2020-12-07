package se.docker.alpine.api.v1;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/v1/packages")
public interface RestfulPackagesApi
{
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    Response packageId() throws IOException;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    Response memberResources();
}
