package net.azisaba.rc.command.skill.ui;

import net.azisaba.rc.command.skill.ICommandSkill;
import net.azisaba.rc.ui.inventory.gamemenu.MyProfileUI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class UIMyProfileSkill implements ICommandSkill
{
    @Override
    public String getName()
    {
        return "ui:myprofile";
    }

    @Override
    public boolean isOPCommand()
    {
        return true;
    }

    @Override
    public void onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (args.length != 1)
        {
            sender.sendMessage(Component.text("Correct syntax: /rc ui:profile <player>").color(NamedTextColor.RED));
            return;
        }

        if (Bukkit.getServer().getPlayerExact(args[0]) == null)
        {
            sender.sendMessage(Component.text(args[0] + " is currently offline.").color(NamedTextColor.RED));
            return;
        }

        Player player = Bukkit.getPlayer(args[0]);
        new MyProfileUI(player);
    }

    @Override
    public ArrayList<String> onTabComplete(CommandSender sender, Command command, String label, String[] args)
    {
        ArrayList<String> list = new ArrayList<>();

        if (args.length == 1)
        {
            Bukkit.getOnlinePlayers().forEach(p -> list.add(p.getName()));
        }

        return list;
    }
}
