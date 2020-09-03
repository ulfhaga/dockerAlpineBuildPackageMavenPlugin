package se.docker.alpine.build.gateway.api.v1;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import java.io.InputStream;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
public class MultipartBody
{
    @FormParam("file")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    private InputStream file;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @FormParam("fileName")
    @PartType(MediaType.TEXT_PLAIN)
    private String name;
}
