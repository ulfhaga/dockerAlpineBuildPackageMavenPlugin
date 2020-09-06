package se.docker.alpine.gateway;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import se.docker.alpine.api.v1.RestfulPackageApi;
import se.docker.alpine.api.v1.RestfulPackagesApi;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;

public class Client
{
    final static String BASE_URI = "http://127.0.0.1:8080";
    final ResteasyClient client;

    public Client()
    {
        ResteasyClientBuilder resteasyClientBuilder = new ResteasyClientBuilder();
        client = resteasyClientBuilder.build();
    }

    public void send(ClientDto clientDto) throws IOException
    {
        String uri = createCollection();
        uri = createCollection();
        if (uri.startsWith(RestfulPackageApi.V_1_PACKAGES))
        {
            String id = uri.substring(RestfulPackageApi.V_1_PACKAGES.length());
            putName(id,clientDto.getName());
            getName(id);
        }
        else
        {
            throw new InternalServerErrorException("Wrong path uri:" + uri);
        }
    }

    private String createCollection()
    {
        ResteasyWebTarget target = client.target(BASE_URI);
        RestfulPackagesApi proxy = target.proxy(RestfulPackagesApi.class);
        Response response = proxy.packageId();
        System.out.println("HTTP code: " + response.getStatus());
        URI location = response.getLocation();
        String path = location.getPath();

        response.close();
        return path;
    }

    private String putName1fdfdgfdg(String path) throws IOException
    {
    //    InputStream stream = Files.newInputStream(Path.of(path));
    //    Entity<InputStream> entity = Entity.entity(stream, MediaType.APPLICATION_OCTET_STREAM);
        Entity<String> entity =  Entity.entity("FFF",MediaType.TEXT_PLAIN);
        Response response =  client.target(path).request().put(entity);
        System.out.println("HTTP code: " + response.getStatus());
        response.close();
        return path;
    }

    private String putName(String path, String name) throws IOException
    {
        ResteasyWebTarget target = client.target(BASE_URI);
        RestfulPackageApi proxy = target.proxy(RestfulPackageApi.class);
        Response response = proxy.setName(Long.valueOf(path),name);
        System.out.println("HTTP code: " + response.getStatus());
        response.close();
        return path;
    }

    private String getName(String path) throws IOException
    {
        String name;
        ResteasyWebTarget target = client.target(BASE_URI);
        RestfulPackageApi proxy = target.proxy(RestfulPackageApi.class);
        Response response = proxy.getName(Long.valueOf(path));
        name = response.readEntity(String.class);
        System.out.println("HTTP code: " + response.getStatus());
        response.close();
        return name;
    }



}
