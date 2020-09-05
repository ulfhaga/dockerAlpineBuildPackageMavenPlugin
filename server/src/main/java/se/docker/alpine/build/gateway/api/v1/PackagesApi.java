package se.docker.alpine.build.gateway.api.v1;

import se.docker.alpine.build.service.PackagesService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import se.docker.alpine.api.v1.*;

@Path("/v1/packages")
public class PackagesApi implements RestfulPackagesApi
{
    @Inject
    PackagesService packagesService;

    @Context
    private UriInfo uriInfo;

    @Override
    @SuppressWarnings("QsUndeclaredPathMimeTypesInspection")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response packageId()
    {
        long id = packagesService.createPackage();

        URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(id)).build();
        return (Response.created(uri).status(Response.Status.CREATED)
                .type(MediaType.APPLICATION_JSON).build());

    }

    @Override
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response memberResources()
    {
        List<String> urls = new ArrayList<>();
        List<Long> ids;
        ids = packagesService.getIds();

        for (Long id : ids)
        {
            URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(id)).build();
            urls.add(uri.toString());
        }

        return (Response.status(Response.Status.OK)
                .entity(urls).build());

    }
}