package se.docker.alpine.compress;


import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TarTest
{
    public static final String SOURCE_TAR = "source.tar";
    private OutputStream fOut;

    @Test
    @Order(1)
    void createTarFolder() throws IOException
    {
        Path sourceDirectory = Paths.get("src", "test", "resources", "testData");
        Path archive = Paths.get("target", SOURCE_TAR);
        Tar.createTarContent(sourceDirectory, archive);
        assertTrue(Files.exists(archive));
    }

    @Test
    @Order(2)
    void decompressTarFile() throws IOException
    {
        Path target = Paths.get("target", "testData");
        Path source = Paths.get("target", SOURCE_TAR);
        Tar.extractTarFile(source, target);
        Path hello = Paths.get("target", "testData", "source", "mypackage-1.0", "hello.sh");
        assertTrue(Files.exists(hello));

    }

    @Test
    @Order(10)
    void createTarContent() throws IOException
    {
        Path sourceDirectory = Paths.get("src", "test", "resources", "testData");
        byte[] archiveContent = Tar.createTarContent(sourceDirectory);
        int len = archiveContent.length;
        assertFalse(len == 0);
        assertTrue(len > 100);

        final String pathTar = "target/archive.tar";
        final Path pathTarFile = Paths.get(pathTar);
        Files.deleteIfExists(pathTarFile);
        writeToFile(archiveContent, pathTar);
        assertTrue(Files.isRegularFile(pathTarFile));
    }

    private void writeToFile(byte[] content, String path) throws IOException
    {
        File file = new File(path);
        try (FileOutputStream fileOutputStream = new FileOutputStream(file))
        {
            fileOutputStream.write(content);
        }
    }
}