package se.docker.alpine.build.abuild;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BuildApkFile
{
    private final ProcessBuilder processBuilder;


    public BuildApkFile(ProcessBuilder processBuilder)
    {
        this.processBuilder = processBuilder;
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
