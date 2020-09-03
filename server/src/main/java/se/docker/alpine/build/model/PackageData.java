package se.docker.alpine.build.model;

public class PackageData
{
    private  String name;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }


    public void clear()
    {
        name = "";
    }
}
