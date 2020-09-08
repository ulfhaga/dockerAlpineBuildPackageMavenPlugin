package se.docker.alpine.build.service;

import org.apache.commons.io.FileUtils;
import se.docker.alpine.build.model.PackageData;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@Transactional
@ApplicationScoped
public class PackagesService
{
    private final ConcurrentHashMap<Long, PackageData> packages
            = new ConcurrentHashMap<>();

    private final AtomicLong index = new AtomicLong();

    private final Path tempDir;

    public PackagesService() throws IOException
    {
        tempDir = Files.createTempDirectory("packages");
    }

    public long createPackage() throws IOException
    {
        long counter = getCounter();
        PackageData packageData = new PackageData();
        packages.put(counter, packageData);
        Path folder = Paths.get(tempDir.toAbsolutePath().toString(),String.valueOf(counter));
        FileUtils.deleteDirectory(folder.toFile());
        Files.createDirectory(folder);
        return counter;
    }

    public PackageData getPackageById(String id)
    {
        return packages.get(Long.valueOf(id));
    }

    public PackageData getPackageById(Long id)
    {
        return packages.get(id);
    }

    public List<Long> getIds()
    {
        return (new ArrayList<>(packages.keySet()));
    }

    synchronized private long getCounter()
    {
        long counter = index.incrementAndGet();
        while (packages.containsKey(counter))
        {
            counter = index.incrementAndGet();
            if (counter >= Long.MAX_VALUE)
            {
                this.index.set(0);
            }
        }
        return counter;
    }
}
