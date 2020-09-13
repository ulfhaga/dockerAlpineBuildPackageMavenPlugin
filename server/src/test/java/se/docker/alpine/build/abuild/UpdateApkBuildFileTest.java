package se.docker.alpine.build.abuild;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.docker.alpine.build.model.PackageData;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

class UpdateApkBuildFileTest
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
    void update_apk_build_file() throws IOException
    {
        PackageData packageData = new PackageData();
        packageData.setName("mypackage");
        packageData.setVersion("1.0");
        packageData.setArch("noarch");
        packageData.setLicense("LGPL-2.1-or-later");
        packageData.setUrl("http:\\/\\/www.github.com");
        packageData.setDescription("Testing");
        Path akpBuildPath = Paths.get("src", "test", "resources", "buildData",UpdateApkBuildFile.APKBUILD);
        Path updatedAkpBuildPath = Paths.get("target",UpdateApkBuildFile.APKBUILD);
        packageData.setSource(akpBuildPath);
        UpdateApkBuildFile updateApkBuildFile = new UpdateApkBuildFile(packageData);
        updateApkBuildFile.updateApkBuildFile(akpBuildPath,updatedAkpBuildPath);

    }
}