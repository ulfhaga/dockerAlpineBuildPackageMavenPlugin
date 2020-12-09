package se.docker.alpine.build.model;

import org.jboss.logging.Logger;
import se.docker.alpine.build.gateway.api.v1.PackageApi;

import java.io.File;
import java.nio.file.Path;

public class PackageData
{
    private static final Logger LOG = Logger.getLogger(PackageApi.class);

    private String name;

    private String version;

    private Integer releaseNumber;

    private transient Path source;

    private String arch;

    private String license;

    private String description;

    private String url;

    private String packageFunction;

    // private transient Path alpinePackage;

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

    public String getPackageFunction()
    {
        return packageFunction;
    }

    public void setPackageFunction(String packageFunction)
    {
        this.packageFunction = packageFunction;
    }

    public void clear()
    {
        name = "";
        source = null;
        version = "";
        releaseNumber = 0;
        arch = "";
        license = "";
        description = "";
        url = "";
        packageFunction = "";
        // alpinePackage = null;
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
            LOG.debug("Error APK package not created");
            return null;
        }
    }

    public Integer getReleaseNumber()
    {
        return releaseNumber;
    }

    public void setReleaseNumber(Integer releaseNumber)
    {
        this.releaseNumber = releaseNumber;
    }

}
