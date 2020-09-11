package gateway;

import java.nio.file.Path;

public class ClientDto
{
    private Path source;
    private String name;

    public Path getSource()
    {
        return source;
    }

    public void setSource(Path source)
    {
        this.source = source;
    }


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
