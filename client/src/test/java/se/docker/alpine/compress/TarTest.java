package se.docker.alpine.compress;


import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TarTest
{
    public static final String SOURCE_TAR = "source.tar";

    @Test
    @Order(1)
    void createTarFolder() throws IOException
    {
        Path sourceDirectory = Paths.get("src","test","resources", "testData");
        Path archive = Paths.get("target", SOURCE_TAR);
        Tar.createTarContent(sourceDirectory,archive);
        assertTrue(Files.exists(archive));
    }

    @Test
    @Order(2)
    void decompressTarFile() throws IOException
    {
        Path target = Paths.get("target","testData");
        Path source = Paths.get("target", SOURCE_TAR);
        Tar.extractTarFile(source,target);
        Path hello = Paths.get("target", "testData", "source", "mypackage-1.0", "hello.sh");
        assertTrue(Files.exists(hello));
    }

    @Test
    @Order(10)
    void createTarContent() throws IOException
    {
        Path sourceDirectory = Paths.get("src","test","resources", "testData");
        byte[] archiveContent = Tar.createTarContent(sourceDirectory);
        int len = archiveContent.length;
        // assertTrue( len > 1093);
    }
}