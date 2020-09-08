package se.docker.alpine.api.v1;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import org.jboss.resteasy.annotations.GZIP;

@Path(RestfulPackageApi.V_1_PACKAGES + "{id}")
public interface RestfulPackageApi
{
    String V_1_PACKAGES = "/v1/packages/";

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    Response postPackageData(@PathParam("id") Long id) throws IOException;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response getPackageData(@PathParam("id") Long id);

    @Path("tarball")
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    Response putTarBall(@PathParam("id") Long id, String body) throws IOException;

    @Path("source")
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    Response putSource(@PathParam("id") Long id, @GZIP byte[] tarStream) throws IOException;

    @Path("name")
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    Response setName(@PathParam("id") Long id, String body);

    @Path("name")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    Response getName(@PathParam("id") Long id);

    @Path("package")
    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Consumes(MediaType.TEXT_PLAIN)
    Response getPackage(@PathParam("id") Long id) throws IOException;
}
