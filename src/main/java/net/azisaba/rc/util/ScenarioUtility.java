package net.azisaba.rc.util;

import net.azisaba.rc.Reincarnation;
import net.azisaba.rc.scenario.ScenarioEngine;
import net.azisaba.rc.ui.CLI;

import java.io.File;
import java.util.logging.Level;

public class ScenarioUtility
{

    public static void mount()
    {
        File[] scenarios = new File(Reincarnation.getPlugin().getDataFolder(), "/scenarios").listFiles();

        if (scenarios == null)
        {
            return;
        }

        for (File scenario : scenarios)
        {
            if (scenario.isFile())
            {
                new ScenarioEngine(scenario.getName());
                Reincarnation.getPlugin().getLogger().log(Level.INFO, CLI.GREEN + String.format("Scenario %s loaded.", scenario.getName()) + CLI.END);
            }
        }
    }
}
