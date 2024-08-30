package net.azisaba.rc.command.skill.scenario;

import net.azisaba.rc.command.skill.ICommandSkill;
import net.azisaba.rc.scenario.Scenario;
import net.azisaba.rc.scenario.ScenarioEngine;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class ScenarioStartSkill implements ICommandSkill
{
    @Override
    public String getName()
    {
        return "scenario:start";
    }

    @Override
    public boolean isOPCommand()
    {
        return true;
    }

    @Override
    public void onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (args.length != 2)
        {
            sender.sendMessage(Component.text("Correct syntax: /rc scenario:start <scenario> <player>").color(NamedTextColor.RED));
            return;
        }

        if (Bukkit.getServer().getPlayerExact(args[1]) == null)
        {
            sender.sendMessage(Component.text(args[1] + " is currently offline.").color(NamedTextColor.RED));
            return;
        }

        ScenarioEngine engine = ScenarioEngine.getInstance(args[0]);

        if (engine == null)
        {
            sender.sendMessage(Component.text(String.format("%s is an unknown scenario engine.", args[0])).color(NamedTextColor.RED));
            return;
        }

        sender.sendMessage(Component.text(String.format("Starting scenario %s…", args[0])).color(NamedTextColor.GREEN));
        Scenario scenario = new Scenario(engine, Bukkit.getPlayer(args[1]));
        scenario.next();
    }

    @Override
    public ArrayList<String> onTabComplete(CommandSender sender, Command command, String label, String[] args)
    {
        final ArrayList<String> list = new ArrayList<>();

        if (args.length == 1)
        {
            ScenarioEngine.getInstances().stream().filter(i -> i.getName().startsWith(args[0])).forEach(i -> list.add(i.getName()));
        }

        if (args.length == 2)
        {
            Bukkit.getOnlinePlayers().stream().filter(p -> p.getName().startsWith(args[1])).forEach(p -> list.add(p.getName()));
        }

        return list;
    }
}
