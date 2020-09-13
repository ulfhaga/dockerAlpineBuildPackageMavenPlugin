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
    public static final Pattern COMPILE_VER = Pattern.compile("(pkgver=.*)");
    public static final Pattern COMPILE_SOURCE = Pattern.compile("(source=.*)");
    public static final Pattern COMPILE_DESC = Pattern.compile("(pkgdesc=.*)");
    public static final Pattern COMPILE_URL = Pattern.compile("(url=.*)");
    public static final Pattern COMPILE_ARCH = Pattern.compile("(arch=.*)");
    public static final Pattern COMPILE_LICENSE = Pattern.compile("(license=.*)");


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
            updatedContent = url(license(description(arch(source(version(content))))));
            writeToFile(updatedContent.getBytes(), updatedApkBuildFile);
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

    private String source(String content)
    {
        String srcName = "\\$pkgname-\\$pkgver.tar";
        String replacement = "source=\"" + srcName + "\"";
        return COMPILE_SOURCE.matcher(content).replaceFirst(replacement);
    }

    private String arch(String content)
    {
        String replacement = "arch=\"" + packageData.getArch() + "\"";
        return COMPILE_ARCH.matcher(content).replaceFirst(replacement);
    }

    private String license(String content)
    {
        String replacement = "license=\"" + packageData.getLicense() + "\"";
        return COMPILE_LICENSE.matcher(content).replaceFirst(replacement);
    }

    private String description(String content)
    {
        String replacement = "pkgdesc=\"" + packageData.getDescription() + "\"";
        return COMPILE_DESC.matcher(content).replaceFirst(replacement);
    }

    private String url(String content)
    {
        String replacement = "url=\"" + packageData.getUrl() + "\"";
        return COMPILE_URL.matcher(content).replaceFirst(replacement);
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
