package net.azisaba.rc.scenario.task;

import net.azisaba.rc.scenario.Scenario;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.regex.Pattern;

public class CommandTask extends ScenarioTask
{
    public static Pattern getPattern()
    {
        return Pattern.compile("\\$Command");
    }

    private final String command;

    public CommandTask(String name, Scenario scenario)
    {
        super(name, scenario);
        this.command = this.scenario.getEngine().getConfig().getString(this.name);
    }

    @Override
    public void run(Player player)
    {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.command);
        this.scenario.next();
    }
}
