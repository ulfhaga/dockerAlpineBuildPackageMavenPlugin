package se.docker.alpine.api.v1;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v1/packages")
public interface RestfulPackagesApi
{
    @SuppressWarnings("QsUndeclaredPathMimeTypesInspection")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    Response packageId();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    Response memberResources();
}
