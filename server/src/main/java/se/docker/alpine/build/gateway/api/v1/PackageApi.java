package se.docker.alpine.build.gateway.api.v1;

import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.GZIP;
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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;


@Path("/v1/packages/{id}")
public class PackageApi implements RestfulPackageApi
{

    private static final Logger LOG = Logger.getLogger(PackageApi.class);

    @PathParam("id")
    private String id;
    final static String FILES_PATH = "/tmp";

    @Inject
    PackagesService packagesService;

    @Context
    private UriInfo uriInfo;

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public Response putMember(@PathParam("id") Long id) throws IOException
    {
        LOG.debug("postPackageData");

        Response response;
        PackageData packageData = packagesService.getPackageById(id);

        if (packageData == null)
        {
            // Create
            long newId = packagesService.createPackage();
            URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(newId)).build();
            response = (Response.created(uri).status(Response.Status.CREATED)
                    .type(MediaType.APPLICATION_JSON).build());
        }
        else
        {
            // Replace
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
    public Response getMember(@PathParam("id") Long id)
    {
        PackageData packageData = packagesService.getPackageById(id);
        if (packageData == null)
        {
            return Response.noContent().build();
        }
        else
        {
            return Response.ok().entity(packageData).build();
        }
    }

    @Override
    @Path("source")
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    public Response putSource(@PathParam("id") Long id, @GZIP byte[] tarStream) throws IOException
    {
        LOG.debug("putSource");
        Response response;
        PackageData packageData;
        packageData = packagesService.getPackageById(id);
        if (packageData == null)
        {
            response = Response.noContent().build();
        }
        else
        {
            java.nio.file.Path path = packageData.getSource();
            if (path != null)
            {
                java.nio.file.Path sourceFile = Paths.get(path.toString(), id + ".tar");
                Files.deleteIfExists(sourceFile);

                File gzipFile = sourceFile.toFile();

                try (FileOutputStream stream = new FileOutputStream(gzipFile))
                {
                    stream.write(tarStream);
                }
                response = Response.ok().build();
            }
            else
            {
                response = Response.serverError().build();
            }
        }
        return response;
    }

    @Path("source")
    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Consumes(MediaType.TEXT_PLAIN)
    @GZIP
    public Response getSource(@PathParam("id") Long id) throws IOException
    {
        LOG.debug("getSource");
        Response response;
        PackageData packageData;
        packageData = packagesService.getPackageById(id);
        if (packageData == null)
        {
            response = Response.noContent().build();
        }
        else
        {
            java.nio.file.Path path = packageData.getSource();
            if (path != null)
            {
                java.nio.file.Path sourceFile = Paths.get(path.toString(), id + ".tar");
                LOG.debugv("sourceFile: {0} ", sourceFile.toAbsolutePath().toString());
                byte[] sourceTar = Files.readAllBytes(sourceFile);
                response = Response.ok().entity(sourceTar).build();
            }
            else
            {
                response = Response.serverError().build();
            }
        }
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
        }
        else
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
        }
        else
        {
            String packageName = packageData.getName();
            response = Response.ok().entity(packageName).build();
        }
        return response;
    }


    @Override
    @Path("version")
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public Response setVersion(@PathParam("id") Long id, String body)
    {
        Response response;
        PackageData packageData;
        packageData = packagesService.getPackageById(id);
        if (packageData == null)
        {
            response = Response.noContent().build();
        }
        else
        {
            packageData.setVersion(body);
            response = Response.ok().build();
        }
        return response;
    }

    @Override
    @Path("version")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public Response getVersion(@PathParam("id") Long id)
    {
        Response response;
        PackageData packageData;
        packageData = packagesService.getPackageById(id);
        if (packageData == null)
        {
            response = Response.noContent().build();
        }
        else
        {
            String packageName = packageData.getVersion();
            response = Response.ok().entity(packageName).build();
        }
        return response;
    }

    @Path("package")
    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Consumes(MediaType.TEXT_PLAIN)
    @GZIP
    public Response getPackage(@PathParam("id") Long id) throws IOException
    {
        LOG.debug("getPackage");
        Response response;
        PackageData packageData;
        packageData = packagesService.getPackageById(id);
        if (packageData == null)
        {
            response = Response.noContent().build();
        }
        else
        {
            if (packagesService.createAlpinePackage(packageData))
            {
                java.nio.file.Path path = packageData.getPackage();
                if (path != null)
                {
                    java.nio.file.Path packageFile = path;
                    LOG.debugv("packageFile: {0} ", packageFile.toAbsolutePath().toString());


                    byte[] sourceTar = Files.readAllBytes(packageFile);
                    response = Response.ok().entity(sourceTar).build();
                }
                else
                {
                    response = Response.serverError().build();
                }
            }
            else
            {
                response = Response.serverError().build();
            }
        }
        return response;
    }

    @Override
    public Response setArch(Long id, String body)
    {
        return null;
    }

    @Override
    public Response getArch(Long id)
    {
        return null;
    }

    @Override
    public Response setLicense(Long id, String body)
    {
        return null;
    }

    @Override
    public Response getLicense(Long id)
    {
        return null;
    }

    @Override
    public Response setDescription(Long id, String body)
    {
        return null;
    }

    @Override
    public Response getDescription(Long id)
    {
        return null;
    }

    @Override
    public Response setUrl(Long id, String body)
    {
        return null;
    }

    @Override
    public Response getUrl(Long id)
    {
        return null;
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
