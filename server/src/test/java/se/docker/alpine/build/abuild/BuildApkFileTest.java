package se.docker.alpine.build.abuild;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.docker.alpine.build.model.PackageData;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class BuildApkFileTest
{

    @BeforeEach
    void setUp()
    {
    }

    @AfterEach
    void tearDown()
    {
    }

    @Test
    void build() throws IOException
    {
        Path sourceFile = Paths.get("src","test","resources","testData","source.tar");

        PackageData packageData = new PackageData();
        packageData.setName("mypackage");
        packageData.setVersion("1.0");
        packageData.setSource(sourceFile);
        BuildApkFile buildApkFile = new BuildApkFile(BuildApkFile.createProcessBuilder(), packageData);
    }

    @Test
    void createProcessBuilder()
    {
    }
}