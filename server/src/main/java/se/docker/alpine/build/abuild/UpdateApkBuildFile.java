package se.docker.alpine.build.abuild;

import se.docker.alpine.build.model.PackageData;

import javax.ws.rs.InternalServerErrorException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

public class UpdateApkBuildFile
{
    public static final String APKBUILD = "APKBUILD";
    public static final Pattern COMPILE_VER  = Pattern.compile("(pkgver=.*)");

    private final PackageData packageData;

    public UpdateApkBuildFile(PackageData packageData)
    {
        this.packageData = packageData;
    }


    public void updateApkBuildFile(Path apkBuildFile, Path updatedApkBuildFile) throws IOException
    {
        String updatedContent;
        if (Files.exists(apkBuildFile))
        {
            String content = new String(Files.readAllBytes(apkBuildFile));
            updatedContent = version(content);

            writeToFile(updatedContent.getBytes(),updatedApkBuildFile);

        }
        else
        {
            throw new InternalServerErrorException();
        }
    }

    private String version(String content)
    {
        String replacement = "pkgver=\"" + packageData.getVersion() + "\"";
        return COMPILE_VER.matcher(content).replaceFirst(replacement);
    }


    private void writeToFile(byte[] content, Path path) throws IOException
    {
        Files.deleteIfExists(path);
        File file = path.toFile();
        try (FileOutputStream fileOutputStream = new FileOutputStream(file))
        {
            fileOutputStream.write(content);
        }
    }

}
