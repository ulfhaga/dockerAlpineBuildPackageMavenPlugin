package se.docker.alpine.build.gateway.api.v1;

import io.quarkus.test.Mock;
import se.docker.alpine.build.model.PackageData;
import se.docker.alpine.build.service.PackagesService;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

@Mock
@ApplicationScoped
public class MockPackagesService extends PackagesService
{
    private final Hashtable<Long, PackageData> packages
            = new Hashtable<>();

    private Long index = 0L;
    private final Path tempDir;
    public MockPackagesService() throws IOException
    {
        tempDir = Files.createTempDirectory("packages");
    }

    @Override
    public long createPackage()
    {
        final PackageData packageData = new PackageData();
        packageData.setSource(tempDir);
        packages.put(++index,packageData);
        return index;
    }

    @Override
    public PackageData getPackageById(String id)
    {
        return packages.get(Long.parseLong(id));
    }

    @Override
    public PackageData getPackageById(Long id)
    {
        return packages.get(id);
    }

    @Override
    public List<Long> getIds()
    {
        return (new ArrayList<>(packages.keySet()));
    }

}
