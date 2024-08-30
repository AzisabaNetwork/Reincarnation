package net.azisaba.rc.command.skill;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public interface ICommandSkill
{
    String getName();

    boolean isOPCommand();

    void onCommand(CommandSender sender, Command command, String label, String[] args);

    ArrayList<String> onTabComplete(CommandSender sender, Command command, String label, String[] args);
}
