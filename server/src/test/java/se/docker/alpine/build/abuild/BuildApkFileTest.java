package se.docker.alpine.build.abuild;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.docker.alpine.build.model.PackageData;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

class BuildApkFileTest
{
    @Test
    void build() throws IOException
    {
        Path sourceFile = Paths.get("src", "test", "resources", "testData", "source.tar");

        PackageData packageData = new PackageData();
        packageData.setName("mypackage");
        packageData.setVersion("1.0");
        packageData.setSource(sourceFile);
        packageData.setArch("noarch");
        packageData.setLicense("LGPL-2.1-or-later");
        packageData.setUrl("http:\\/\\/www.github.com");
        packageData.setPackageFunction("install -Dm755 hello.sh \"$pkgdir\"/usr/bin/hello1.sh");
        BuildApkFile buildApkFile = new BuildApkFile(BuildApkFile.createProcessBuilder(), packageData);
    }

    @Test
    void createProcessBuilder()
    {
    }
}