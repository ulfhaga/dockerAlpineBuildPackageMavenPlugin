package se.docker.alpine.gateway;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


class ClientTestIT
{
    @DisplayName("Restful tests")
    @Test
    public void test1() throws IOException
    {
        Path sourceDirectory = Paths.get("src","it","resources","source");
      //  String aaaa = resourceDirectory.toAbsolutePath().toString();

        ClientDto clientDto = new ClientDto();
        clientDto.setName("MyGoodPackage");
        clientDto.setSource(sourceDirectory);
        Client client = new Client();
        client.send(clientDto);
    }

}