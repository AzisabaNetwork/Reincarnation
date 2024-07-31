package net.azisaba.rc.util;

import net.azisaba.rc.Reincarnation;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigurationUtil
{

    public static File getFile(String name)
    {
        return new File(Reincarnation.getPlugin().getDataFolder(), name);
    }

    public static YamlConfiguration getConfiguration(String name)
    {
        return YamlConfiguration.loadConfiguration(ConfigurationUtil.getFile(name));
    }

    public static void saveConfiguration(String name, YamlConfiguration configuration)
    {
        try
        {
            configuration.save(ConfigurationUtil.getFile(name));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
