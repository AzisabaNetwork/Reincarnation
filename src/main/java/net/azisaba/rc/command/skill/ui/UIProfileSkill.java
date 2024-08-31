package net.azisaba.rc.command.skill.ui;

import net.azisaba.rc.command.skill.ICommandSkill;
import net.azisaba.rc.ui.inventory.ProfileUI;
import net.azisaba.rc.user.User;
import net.azisaba.rc.util.UuidUtility;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.UUID;

public class UIProfileSkill implements ICommandSkill
{
    @Override
    public String getName()
    {
        return "ui:profile";
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
            sender.sendMessage(Component.text(String.format("Correct syntax: /rc %s <player> <view>", this.getName())).color(NamedTextColor.RED));
            return;
        }

        if (Bukkit.getServer().getPlayerExact(args[0]) == null)
        {
            sender.sendMessage(Component.text(args[0] + " is currently offline.").color(NamedTextColor.RED));
            return;
        }

        if (! UuidUtility.isUuid(args[1]))
        {
            sender.sendMessage(Component.text(String.format("第二引数にはUUIDを指定してください: %s", args[1])).color(NamedTextColor.RED));
            return;
        }

        new ProfileUI(Bukkit.getPlayer(args[0]), User.getInstance(UUID.fromString(args[1])));
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
