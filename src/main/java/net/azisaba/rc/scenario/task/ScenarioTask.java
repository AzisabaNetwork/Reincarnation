package net.azisaba.rc.scenario.task;

import net.azisaba.rc.scenario.Scenario;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.regex.Pattern;

public abstract class ScenarioTask
{

    public static Pattern getPattern()
    {
        return Pattern.compile("");
    }

    protected final String name;
    protected final Scenario scenario;
    protected final ConfigurationSection parameters;

    public ScenarioTask(String name, Scenario scenario)
    {
        this.name = name;
        this.scenario = scenario;
        this.parameters = scenario.getEngine().getConfig().getConfigurationSection(name);
    }

    public void onPlayerInteract(PlayerInteractEvent event)
    {

    }

    public void run(Player player)
    {

    }
}