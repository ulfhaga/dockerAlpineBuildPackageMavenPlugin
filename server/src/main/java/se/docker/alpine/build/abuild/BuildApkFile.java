package se.docker.alpine.build.abuild;

import se.docker.alpine.build.model.PackageData;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.function.Supplier;
import org.jboss.logging.Logger;

/**
 *  Build an APK
 */
public class BuildApkFile
{
    private static final Logger LOG = Logger.getLogger(BuildApkFile.class);
    public static final String APK_BUILD = "APKBUILD";
    private final ProcessBuilder processBuilder;
    private final PackageData packageData;
    private final Path aPortsFolder;
    private final Path packageFolder;
    private final Path targetFolder;


    public BuildApkFile(ProcessBuilder processBuilder, PackageData packageData) throws IOException
    {
        this.processBuilder = processBuilder;
        this.packageData = packageData;
        Path tempDir = Files.createTempDirectory("aports");
        aPortsFolder = Paths.get(tempDir.toAbsolutePath().toString(), "aports");
        Files.createDirectory(aPortsFolder);

        // Create package folder
        packageFolder = Paths.get(aPortsFolder.toAbsolutePath().toString(), packageData.getName());

        targetFolder = aPortsFolder.getParent();
        Path targetFile = Paths.get(aPortsFolder.toAbsolutePath().toString(),packageData.getName());
    }

    private void copyTarFileToArkBuild(PackageData packageData) throws IOException
    {
        String tarFile = packageData.getName() + "-" + packageData.getVersion() + ".tar";
        Path targetFile = Paths.get(aPortsFolder.toAbsolutePath().toString(),packageData.getName(),tarFile);
        Path sourceTarFile =  Paths.get(packageData.getSource().toAbsolutePath().toString(),"source.tar");
        Files.copy(sourceTarFile, targetFile);
    }

    public void run() throws IOException
    {
        buildApkFile(targetFolder);
        UpdateApkBuildFile updateApkBuildFile = new UpdateApkBuildFile(packageData);
        Path apkBuildFile = Paths.get(aPortsFolder.toAbsolutePath().toString(),packageData.getName(),"APKBUILD");
        updateApkBuildFile.updateApkBuildFile(apkBuildFile,apkBuildFile);
        copyTarFileToArkBuild(packageData);
    }

    private int buildApkFile(Path workFolder)
    {
        String[] commandNewApkBuild = {"newapkbuild", this.packageData.getName()};
        Supplier<String[]> command = () -> commandNewApkBuild;
        int exitVal = command(packageData.getName(), aPortsFolder, command);
        return exitVal;
    }

    private int command(String packageName, Path workFolder, Supplier<String[]> supplier)
    {
        int exitVal = 0;
        processBuilder.command(supplier.get());
        try
        {
            Map<String, String> env = processBuilder.environment();
            processBuilder.directory(workFolder.toFile());
            Process process = processBuilder.start();
            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null)
            {
                output.append(line + "\n");
            }
            exitVal = process.waitFor();
            if (exitVal == 0)
            {
                LOG.debugv("Success: %s ", output);
            }
            else
            {
                LOG.errorf("ERROR: %s ", output);
            }
        }
        catch (IOException | InterruptedException e)
        {
            LOG.errorf("ERROR: %s ", e.getMessage());
            exitVal = 2;
        }
        return exitVal;
    }

    private void writeToFile(byte[] content, String path) throws IOException
    {
        File file = new File(path);
        try (FileOutputStream fileOutputStream = new FileOutputStream(file))
        {
            fileOutputStream.write(content);
        }
    }

    static public ProcessBuilder createProcessBuilder()
    {
        return new ProcessBuilder();
    }

    public int buildApkPackage()
    {
        String[] commandNewApkBuild = {"abuild", "checksum"};
        Supplier<String[]> command = () -> commandNewApkBuild;
        int exitVal = command(packageData.getName(), packageFolder, command);

        if (exitVal == 0 )
        {
            String[] commandABuild = {"abuild", "-r"};
            command = () -> commandABuild;
            exitVal = command(packageData.getName(), packageFolder, command);
        }
        return exitVal;
    }
}
