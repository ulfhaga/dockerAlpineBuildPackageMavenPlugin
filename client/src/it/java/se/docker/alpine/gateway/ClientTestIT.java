package se.docker.alpine.gateway;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;


@Tag("dd")
class ClientTestIT
{
    @DisplayName("Restful tests")
    @Test
    public void test1() throws IOException
    {
        Client client = new Client();
        client.send();
    }

}