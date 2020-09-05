package se.docker.alpine.mojo;

import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "create")
public class PackageMojo implements org.apache.maven.plugin.Mojo
{
    private Log log;

    @Parameter(property = "source", required = true)
    private String source;

    @Override
    public void execute()
    {
        System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");
        log.info("Hello, Ulf's world");
        log.info("Source:" + source);
    }

    @Override
    public void setLog(Log log)
    {
        this.log = log;
    }

    @Override
    public Log getLog()
    {
        return log;
    }
}