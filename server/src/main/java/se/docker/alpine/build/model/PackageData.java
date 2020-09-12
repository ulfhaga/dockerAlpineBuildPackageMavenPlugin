package se.docker.alpine.build.model;

import java.nio.file.Path;

public class PackageData
{
    private String name;


    private String version;

    private transient Path source;

    private transient Path alpinePackage;

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

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public void clear()
    {
        name = "";
        source = null;
    }

    public Path getPackage()
    {
        return this.alpinePackage;
    }
}
