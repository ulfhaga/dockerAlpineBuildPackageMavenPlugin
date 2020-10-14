package se.docker.alpine.build.abuild;

import se.docker.alpine.build.model.PackageData;

import javax.ws.rs.InternalServerErrorException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.function.Supplier;

public class BuildApkFile
{
    public static final String APKBUILD = "APKBUILD";
    private final ProcessBuilder processBuilder;
    private final PackageData packageData;
    private final Path tempDir;
    private final Path aportsFolder;
    private final Path packageFolder;
    private final Path targetFolder;


    public BuildApkFile(ProcessBuilder processBuilder, PackageData packageData) throws IOException
    {
        this.processBuilder = processBuilder;
        this.packageData = packageData;
        tempDir = Files.createTempDirectory("aports");
        aportsFolder = Paths.get(tempDir.toAbsolutePath().toString(), "aports");
        Files.createDirectory(aportsFolder);

        // Create package folder
        // packageFolder = Paths.get(aportsFolder.toAbsolutePath().toString(), packageData.getName());
        // Files.createDirectory(packageFolder);

        // Copy tar ball

      /*  String fileNameWithOutExt = FilenameUtils.removeExtension(sourceTarFile.getFileName().toString());
        String extension = FilenameUtils.getExtension(sourceTarFile.getFileName().toString());
        final String newFilename = fileNameWithOutExt + "-" + packageData.getVersion() + "." + extension;
        Path targetFile = Paths.get(packageFolder.toAbsolutePath().toString(),
                newFilename);

        targetFolder = targetFile.getParent();
        */
        targetFolder = aportsFolder.getParent();
        packageFolder = null;
    }

    private void copyTarFileToArkBuild(PackageData packageData) throws IOException
    {
        String tarFile = packageData.getName() + "-" + packageData.getVersion() + ".tar";
        Path targetFile = Paths.get(aportsFolder.toAbsolutePath().toString(),packageData.getName(),tarFile);
        Path sourceTarFile = packageData.getSource();
        Files.copy(sourceTarFile, targetFile);
    }

    public void run() throws IOException
    {
        buildApkFile(targetFolder);
        UpdateApkBuildFile updateApkBuildFile = new UpdateApkBuildFile(packageData);
        Path apkBuildFile = Paths.get(aportsFolder.toAbsolutePath().toString(),packageData.getName(),"APKBUILD");
        updateApkBuildFile.updateApkBuildFile(apkBuildFile,apkBuildFile);
        copyTarFileToArkBuild(packageData);
    }

    private int buildApkFile(Path workFolder)
    {
        String[] commandNewApkBuild = {"newapkbuild", this.packageData.getName()};
        Supplier<String[]> command = () -> commandNewApkBuild;
        int exitVal = command(packageData.getName(), workFolder, command);
        return exitVal;
    }

    private void updateApkBuildFile() throws IOException
    {
        Path apkBuild = Paths.get(targetFolder.toString(), APKBUILD);
        if (Files.exists(apkBuild))
        {
            UpdateApkBuildFile updateApkBuildFile = new UpdateApkBuildFile(packageData);
        }
        else
        {
            throw new InternalServerErrorException();
        }

    }

    private int command(String packageName, Path workFolder, Supplier<String[]> supplier)
    {
        int exitVal = 0;
        processBuilder.command(supplier.get());
        try
        {
            Map<String, String> env = processBuilder.environment();
            //env.put("user.dir", workFolder.toAbsolutePath().toString());
            processBuilder.directory(aportsFolder.toFile());
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
                System.out.println("Success!");
                System.out.println(output);
            }
            else
            {
                //abnormal...
            }

        }
        catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
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
}
