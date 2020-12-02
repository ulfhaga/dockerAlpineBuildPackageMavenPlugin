package se.docker.alpine.mojo;

import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import se.docker.alpine.gateway.Client;
import se.docker.alpine.gateway.ClientDto;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Mojo(name = "create")
public class PackageMojo implements org.apache.maven.plugin.Mojo
{
    private Log log;

    @Parameter(property = "source", required = true)
    private String source;

    @Parameter(property = "name", required = true)
    private String name;

    @Parameter(property = "version", required = true)
    private String version;

    @Parameter(property = "arch", required = true)
    private String arch;

    @Parameter(property = "license", required = true)
    private String license;

    @Parameter(property = "url", required = true)
    private String url;

    @Parameter(property = "description", required = true)
    private String description;

    @Parameter(property = "target", required = true)
    private String target;

    @Parameter(property = "packageFunction", required = true)
    private String packageFunction;

    @Override
    public void execute()
    {
        log.info("Source:" + source);
        log.info("Name:" + name);


        Client client = new Client();
        ClientDto clientDto = getClientDto();
        try
        {
            client.send(clientDto);
        }
        catch (IOException e)
        {
            log.error(e.getMessage());
            e.printStackTrace();
        }

    }

    private ClientDto getClientDto()
    {
        ClientDto clientDto = new ClientDto();

        Path sourceDirectory = Paths.get(source);
        clientDto.setSource(sourceDirectory);
        clientDto.setName(name);
        clientDto.setVersion(version);
        clientDto.setArch(arch);
        clientDto.setLicense(license);
        clientDto.setUrl(url);
        clientDto.setDescription(description);
        clientDto.setPackageFunction(packageFunction);
        Path targetDirectory = Paths.get(target);
        clientDto.setTarget(targetDirectory);
        return clientDto;
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