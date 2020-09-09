package se.docker.alpine.gateway;

import gateway.Client;
import gateway.ClientDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


class ClientTestIT
{
    @Disabled
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
     //   client.send(clientDto);
    }

}