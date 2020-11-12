package se.docker.alpine.build.model;

import java.io.File;
import java.nio.file.Path;

public class PackageData
{
    private String name;

    private String version;

    private transient Path source;

    private String arch;

    private String license;

    private String description;

    private String url;

    private transient Path alpinePackage;

    public String getArch()
    {
        return arch;
    }

    public void setArch(String arch)
    {
        this.arch = arch;
    }

    public String getLicense()
    {
        return license;
    }

    public void setLicense(String license)
    {
        this.license = license;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

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
        version = "";
        arch = "";
        license = "";
        description = "";
        url = "";
        alpinePackage = null;
    }

    public Path getPackage()
    {
        File path = new File("/home/dev/packages/aports/x86_64/" + getName() + "-" + getVersion() + "-r0" + ".apk");
        if (path.exists())
        {
            return path.toPath();
        }
        else
        {
            return null;
        }
    }
}
