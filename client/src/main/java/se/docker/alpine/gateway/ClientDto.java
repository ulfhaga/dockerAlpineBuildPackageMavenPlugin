package se.docker.alpine.gateway;

import java.io.File;

public class ClientDto
{
    private File source;
    private String name;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
