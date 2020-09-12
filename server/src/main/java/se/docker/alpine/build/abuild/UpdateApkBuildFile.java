package se.docker.alpine.build.abuild;

import se.docker.alpine.build.model.PackageData;

import javax.ws.rs.InternalServerErrorException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UpdateApkBuildFile
{
    public static final String APKBUILD = "APKBUILD";

    private final PackageData packageData;

    public UpdateApkBuildFile(PackageData packageData)
    {
        this.packageData = packageData;
    }


    private void update_apk_build_file(Path sourceFolder) throws IOException
    {
        Path apkBuild = Paths.get(sourceFolder.toString(), APKBUILD);
        if (Files.exists(apkBuild))
        {
            String content = new String(Files.readAllBytes(apkBuild));
            content.replaceFirst("$pkgver=\"\"", "pkgver=\"" + packageData.getVersion() + "\"");
        }
        else
        {
            throw new InternalServerErrorException();
        }
    }

}
