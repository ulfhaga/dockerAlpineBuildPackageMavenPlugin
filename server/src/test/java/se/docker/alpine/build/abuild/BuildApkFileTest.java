package se.docker.alpine.build.abuild;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.docker.alpine.build.model.PackageData;

import java.io.IOException;

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
        PackageData packageData = new PackageData();
        BuildApkFile buildApkFile = new BuildApkFile(BuildApkFile.createProcessBuilder(), packageData);
    }

    @Test
    void createProcessBuilder()
    {
    }
}