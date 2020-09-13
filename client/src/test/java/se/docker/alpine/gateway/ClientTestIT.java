package se.docker.alpine.gateway;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


class ClientTestIT
{
    @DisplayName("Restful tests")
    @Test
    public void test1() throws IOException
    {
        Path sourceDirectory = Paths.get("src","test","resources","testData","source");
        ClientDto clientDto = new ClientDto();
        clientDto.setName("MyGoodPackage");
        clientDto.setSource(sourceDirectory);

        clientDto.setVersion("1.0");
        clientDto.setArch("noarch");
        clientDto.setLicense("LGPL-2.1-or-later");
        clientDto.setUrl("http:\\/\\/www.github.com");
        clientDto.setDescription("Testing");

        Client client = new Client();
        client.send(clientDto);
    }
}