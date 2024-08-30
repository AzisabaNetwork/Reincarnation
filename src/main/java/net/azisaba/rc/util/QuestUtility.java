package net.azisaba.rc.util;

import net.azisaba.rc.Reincarnation;
import net.azisaba.rc.quest.Quest;
import net.azisaba.rc.quest.QuestEngine;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

public class QuestUtility
{
    public static String getId()
    {
        String id = null;
        boolean loopFlag = true;

        while (loopFlag)
        {
            id = UUID.randomUUID().toString();
            loopFlag = Quest.getInstance(id) != null;
        }

        return id;
    }

    public static boolean test(String name)
    {
        if (name.contains(" ") || name.contains("　"))
        {
            Reincarnation.getPlugin().getLogger().warning( name + " is an invalid file name. It cannot contain spaces.");
            return false;
        }

        YamlConfiguration config = ResourceUtility.getYamlResource("/quests/" + name);

        ArrayList<String> requiredParameters = new ArrayList<>();
        requiredParameters.add("Display");
        requiredParameters.add("Favicon");
        requiredParameters.add("Type");
        requiredParameters.add("Lore");
        requiredParameters.add("Count");
        requiredParameters.add("MaxMember");
        requiredParameters.add("Amount");
        requiredParameters.add("Spawn.World");
        requiredParameters.add("Spawn.X");
        requiredParameters.add("Spawn.Y");
        requiredParameters.add("Spawn.Z");
        requiredParameters.add("Start");
        requiredParameters.add("End");

        for (String requiredParameter : requiredParameters)
        {
            if (! config.contains(requiredParameter))
            {
                Reincarnation.getPlugin().getLogger().warning(String.format("The required parameter %s is not defined in %s.", requiredParameter, name));
                return false;
            }
        }

        return true;
    }

    public static boolean mount()
    {
        File quests = new File(Reincarnation.getPlugin().getDataFolder(), "/quests");

        if (! quests.exists())
        {
            quests.mkdirs();
        }

        File[] configs = quests.listFiles();

        if (configs == null)
        {
            return true;
        }

        for (File config : configs)
        {
            if (config.isFile())
            {
                if (! QuestUtility.test(config.getName()))
                {
                    Reincarnation.getPlugin().getServer().getPluginManager().disablePlugin(Reincarnation.getPlugin());
                    return false;
                }

                String id;
                int dotIndex = config.getName().lastIndexOf(".");

                if (dotIndex > 0 && dotIndex < config.getName().length() - 1)
                {
                    id = config.getName().substring(0, dotIndex);
                }
                else
                {
                    id = config.getName();
                }

                new QuestEngine(id);
            }
        }

        return true;
    }
}
