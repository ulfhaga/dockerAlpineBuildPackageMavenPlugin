package se.docker.alpine.build.gateway.api.v1;

import se.docker.alpine.api.v1.RestfulPackageApi;
import se.docker.alpine.build.model.PackageData;
import se.docker.alpine.build.service.PackagesService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Base64;
import java.util.UUID;

@Path("/v1/packages/{id}")
public class PackageApi implements RestfulPackageApi
{
    @PathParam("id")
    private String id;
    final static String FILES_PATH = "/tmp";

    @Inject
    PackagesService packagesService;

    @Context
    private UriInfo uriInfo;

    @Override
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public Response postPackageData(@PathParam("id") Long id)
    {
        Response response;
        PackageData packageData = packagesService.getPackageById(id);

        if (packageData == null)
        {
            long newId = packagesService.createPackage();
            URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(newId)).build();
            response = (Response.created(uri).status(Response.Status.CREATED)
                    .type(MediaType.APPLICATION_JSON).build());
        } else
        {
            packageData.clear();
            URI uri = uriInfo.getAbsolutePathBuilder().build();
            response = (Response.created(uri).status(Response.Status.CREATED)
                    .type(MediaType.APPLICATION_JSON).build());
        }
        return response;
    }

    @Override
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPackageData(@PathParam("id") Long id)
    {
        PackageData packageData = packagesService.getPackageById(id);
        if (packageData == null)
        {
            return Response.noContent().build();
        } else
        {
            return Response.ok().entity(packageData).build();
        }
    }

    @Override
    @Path("tarball")
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public Response putTarBall(@PathParam("id") Long id, String body) throws IOException
    {
        Response response;
        byte[] decodedBytes = Base64.getDecoder().decode(body);
        String decodedString = new String(decodedBytes);
        response = Response.ok().build();
        return response;
    }

    @Override
    @Path("name")
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public Response setName(@PathParam("id") Long id, String body)
    {
        Response response;
        PackageData packageData;
        packageData = packagesService.getPackageById(id);
        if (packageData == null)
        {
            response = Response.noContent().build();
        } else
        {
            packageData.setName(body);
            response = Response.ok().build();
        }
        return response;
    }

    @Override
    @Path("name")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public Response getName(@PathParam("id") Long id)
    {
        Response response;
        PackageData packageData;
        packageData = packagesService.getPackageById(id);
        if (packageData == null)
        {
            response = Response.noContent().build();
        } else
        {
            String packageName = packageData.getName();
            response = Response.ok().entity(packageName).build();
        }
        return response;
    }


    //Convert a Base64 string and create a file
    private String convertFile(String dataBase64)
            throws IOException
    {
        byte[] bytes = Base64.getDecoder().decode(dataBase64);
        String uuid = UUID.randomUUID().toString();
        File file = new File(FILES_PATH + File.separator + uuid);

        try (FileOutputStream fos = new FileOutputStream(file))
        {
            fos.write(bytes);
            fos.flush();
        }
        return uuid;
    }

}
