package se.docker.alpine.gateway;

import java.nio.file.Path;

public class ClientDto
{
    private Path source;
    private String name;
    private String version;
    private String arch;
    private String license;
    private String description;
    private String url;

    public Path getSource()
    {
        return source;
    }

    public void setSource(Path source)
    {
        this.source = source;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }
    public void setArch(String arch)
    {
        this.arch = arch;
    }

    public void setLicense(String license)
    {
        this.license = license;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}
