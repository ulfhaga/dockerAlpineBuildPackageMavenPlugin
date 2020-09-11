package se.docker.alpine.build.abuild;

import se.docker.alpine.build.model.PackageData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BuildApkFile
{
    private final ProcessBuilder processBuilder;
    private final PackageData packageData;
    private final Path tempDir;
    private final Path aportsFolder;


    public BuildApkFile(ProcessBuilder processBuilder, PackageData packageData) throws IOException
    {
        this.processBuilder = processBuilder;
        this.packageData = packageData;
        tempDir = Files.createTempDirectory("aports");
        aportsFolder = Paths.get(tempDir.toAbsolutePath().toString(), "aports");
        Files.createDirectory(aportsFolder);
    }

    public int build(String packageName, String workFolder)
    {
        int exitVal = 100;
        // docker exec -t -w /home/dev/aports apk-build newapkbuild "${package_name}"
        processBuilder.command("docker", "exec", "-t", "-w", workFolder, "apk-build", "newapkbuild", packageName);
        try
        {
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

    static public ProcessBuilder createProcessBuilder()
    {
        return new ProcessBuilder();
    }
}
