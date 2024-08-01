package net.azisaba.rc.command.skill;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public abstract class RcCommandSkill
{
    private final String name;

    protected boolean opcmd = true;

    public RcCommandSkill(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public boolean isOPCommand()
    {
        return this.opcmd;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        return false;
    }

    public ArrayList<String> onTabComplete(CommandSender sender, Command command, String label, String[] args)
    {
        return null;
    }

    @Override
    public String toString()
    {
        return this.name;
    }
}
