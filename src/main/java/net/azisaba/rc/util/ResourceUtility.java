package net.azisaba.rc.util;

import net.azisaba.rc.Reincarnation;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ResourceUtility
{
    public static File getResource(String name)
    {
        return new File(Reincarnation.getPlugin().getDataFolder(), name);
    }

    public static YamlConfiguration getYamlResource(String name)
    {
        return YamlConfiguration.loadConfiguration(ResourceUtility.getResource(name));
    }

    public static void save(String name, YamlConfiguration resource)
    {
        try
        {
            resource.save(ResourceUtility.getResource(name));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
