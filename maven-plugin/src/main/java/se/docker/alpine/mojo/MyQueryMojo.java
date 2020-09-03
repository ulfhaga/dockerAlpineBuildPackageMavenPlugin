package se.docker.alpine.mojo;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Parameter;

public class MyQueryMojo extends AbstractMojo
{
    private Log log;

    @Parameter(property = "query.url", required = true)
    private String url;

    @Parameter(property = "timeout", required = false, defaultValue = "50")
    private int timeout;

    @Parameter(property = "options")
    private String[] options;

    public void execute()
            throws MojoExecutionException
    {
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        //  log.info("Hello, Ulf's world");
    }
}