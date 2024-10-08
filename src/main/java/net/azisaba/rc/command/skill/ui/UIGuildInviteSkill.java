package net.azisaba.rc.command.skill.ui;

import net.azisaba.rc.command.skill.ICommandSkill;
import net.azisaba.rc.guild.Guild;
import net.azisaba.rc.ui.inventory.gamemenu.GuildInviteUI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class UIGuildInviteSkill implements ICommandSkill
{
    @Override
    public String getName()
    {
        return "ui:guild-invite";
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
            sender.sendMessage(Component.text(String.format("Correct syntax: /rc %s <player> <guild>", this.getName())).color(NamedTextColor.RED));
            return;
        }

        if (Bukkit.getServer().getPlayerExact(args[0]) == null)
        {
            sender.sendMessage(Component.text(args[0] + " is currently offline.").color(NamedTextColor.RED));
            return;
        }

        Player player = Bukkit.getPlayer(args[0]);
        new GuildInviteUI(player, Guild.getInstance(UUID.fromString(args[1])));
    }

    @Override
    public ArrayList<String> onTabComplete(CommandSender sender, Command command, String label, String[] args)
    {
        ArrayList<String> list = new ArrayList<>();

        if (args.length == 1)
        {
            Bukkit.getOnlinePlayers().forEach(p -> list.add(p.getName()));
        }

        if (args.length == 2)
        {
            Guild.getInstances().forEach(i -> list.add(i.getId().toString()));
        }

        return list;
    }
}
