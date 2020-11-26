package se.docker.alpine.gateway;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import se.docker.alpine.api.v1.RestfulPackageApi;
import se.docker.alpine.api.v1.RestfulPackagesApi;
import se.docker.alpine.compress.Tar;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Response;
import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Client
{
    final static String BASE_URI = "http://127.0.0.1:64014";
    final ResteasyClient client;

    public Client()
    {
        ResteasyClientBuilder resteasyClientBuilder = new ResteasyClientBuilder();
        client = resteasyClientBuilder.build();
    }

    public void send(ClientDto clientDto) throws IOException
    {
        String uri;
        uri = createCollection();
        if (uri.startsWith(RestfulPackageApi.V_1_PACKAGES))
        {
            String id = uri.substring(RestfulPackageApi.V_1_PACKAGES.length());
            putName(id,clientDto.getName());
            putVersion(id,clientDto.getVersion());
            putArch(id,clientDto.getArch());
            putLicence(id,clientDto.getLicense());
            putDescription(id,clientDto.getDescription());
            putUrl(id,clientDto.getUrl());
            putSource(id,clientDto.getSource(), clientDto.getName(), clientDto.getVersion());
            byte[] packageAkp = getPackage(id);

            if ( Files.notExists(clientDto.getTarget()))
            {
                Files.createDirectory(clientDto.getTarget());
            }
            Path apkFile = Paths.get(clientDto.getTarget().toAbsolutePath().toString(), clientDto.getName() + "-" + clientDto.getVersion() + ".apk");
            try (FileOutputStream stream = new FileOutputStream(apkFile.toAbsolutePath().toString())) {
                stream.write(packageAkp);
            }

        }
        else
        {
            throw new InternalServerErrorException("Wrong path uri:" + uri);
        }
    }

    private void putUrl(String path, String description)
    {
        ResteasyWebTarget target = client.target(BASE_URI);
        RestfulPackageApi proxy = target.proxy(RestfulPackageApi.class);
        Response response = proxy.setUrl(Long.valueOf(path),description);
        System.out.println("HTTP code: " + response.getStatus());
        response.close();
    }

    private void putDescription(String path, String description)
    {
        ResteasyWebTarget target = client.target(BASE_URI);
        RestfulPackageApi proxy = target.proxy(RestfulPackageApi.class);
        Response response = proxy.setDescription(Long.valueOf(path),description);
        System.out.println("HTTP code: " + response.getStatus());
        response.close();
    }

    private void putLicence(String path, String license)
    {
        ResteasyWebTarget target = client.target(BASE_URI);
        RestfulPackageApi proxy = target.proxy(RestfulPackageApi.class);
        Response response = proxy.setLicense(Long.valueOf(path),license);
        System.out.println("HTTP code: " + response.getStatus());
        response.close();
    }

    private void putArch(String path, String arch)
    {
        ResteasyWebTarget target = client.target(BASE_URI);
        RestfulPackageApi proxy = target.proxy(RestfulPackageApi.class);
        Response response = proxy.setArch(Long.valueOf(path),arch);
        System.out.println("HTTP code: " + response.getStatus());
        response.close();
    }

    private String getArch(String path) throws IOException
    {
        String arch;
        ResteasyWebTarget target = client.target(BASE_URI);
        RestfulPackageApi proxy = target.proxy(RestfulPackageApi.class);
        Response response = proxy.getArch(Long.valueOf(path));
        arch = response.readEntity(String.class);
        System.out.println("HTTP code: " + response.getStatus());
        response.close();
        return arch;
    }

    private void putVersion(String path, String version)
    {
        ResteasyWebTarget target = client.target(BASE_URI);
        RestfulPackageApi proxy = target.proxy(RestfulPackageApi.class);
        Response response = proxy.setVersion(Long.valueOf(path),version);
        System.out.println("HTTP code: " + response.getStatus());
        response.close();
    }

    private String getVersion(String path) throws IOException
    {
        String version;
        ResteasyWebTarget target = client.target(BASE_URI);
        RestfulPackageApi proxy = target.proxy(RestfulPackageApi.class);
        Response response = proxy.getVersion(Long.valueOf(path));
        version = response.readEntity(String.class);
        System.out.println("HTTP code: " + response.getStatus());
        response.close();
        return version;
    }

    private String createCollection() throws IOException
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
    
    private void putName(String path, String name) throws IOException
    {
        ResteasyWebTarget target = client.target(BASE_URI);
        RestfulPackageApi proxy = target.proxy(RestfulPackageApi.class);
        Response response = proxy.setName(Long.valueOf(path),name);
        System.out.println("HTTP code: " + response.getStatus());
        response.close();
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


    private void putSourceCC(String id, Path sourceFolder) throws IOException
    {
        byte[] tarFileContent = Tar.createTarContent(sourceFolder);
        ResteasyWebTarget target = client.target(BASE_URI);
        RestfulPackageApi proxy = target.proxy(RestfulPackageApi.class);
        Response response = proxy.putSource(Long.valueOf(id),tarFileContent);
        System.out.println("HTTP code: " + response.getStatus());
        response.close();
    }

    private void putSource(String id, Path sourceFolder,String packName, String version) throws IOException
    {
        byte[] tarFileContent = Tar.createApkTarContent(sourceFolder,packName, version);
        ResteasyWebTarget target = client.target(BASE_URI);
        RestfulPackageApi proxy = target.proxy(RestfulPackageApi.class);
        Response response = proxy.putSource(Long.valueOf(id),tarFileContent);
        System.out.println("HTTP code: " + response.getStatus());
        response.close();
    }

    private byte[] getPackage(String path) throws IOException
    {
        byte[] name;
        ResteasyWebTarget target = client.target(BASE_URI);
        RestfulPackageApi proxy = target.proxy(RestfulPackageApi.class);
        Response response = proxy.getPackage(Long.valueOf(path));
        name = response.readEntity(byte[].class);
        System.out.println("HTTP code: " + response.getStatus());
        response.close();
        return name;
    }



}
