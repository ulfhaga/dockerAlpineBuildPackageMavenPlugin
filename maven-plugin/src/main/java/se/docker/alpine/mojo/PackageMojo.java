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
        clientDto.setName(name);

        Path sourceDirectory = Paths.get(source);
        clientDto.setSource(sourceDirectory);

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