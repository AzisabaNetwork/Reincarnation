package net.azisaba.rc.command.skill.guild;

import net.azisaba.rc.command.skill.ICommandSkill;
import net.azisaba.rc.guild.Guild;
import net.azisaba.rc.user.User;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class GuildJoinSkill implements ICommandSkill
{
    @Override
    public String getName()
    {
        return "guild:join";
    }

    @Override
    public boolean isOPCommand()
    {
        return false;
    }

    @Override
    public void onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (args.length != 2)
        {
            sender.sendMessage(Component.text("Correct syntax: /rc guild:join <guild> <player>").color(NamedTextColor.RED));
            return;
        }

        if (Guild.getInstance(UUID.fromString(args[0])) == null)
        {
            sender.sendMessage(Component.text(args[0] + " is an unknown guild.").color(NamedTextColor.RED));
            return;
        }

        if (Bukkit.getServer().getPlayerExact(args[1]) == null)
        {
            sender.sendMessage(Component.text(args[1] + " is currently offline.").color(NamedTextColor.RED));
            return;
        }

        Player player = Bukkit.getPlayer(args[1]);
        Guild guild = Guild.getInstance(UUID.fromString(args[0]));

        if (guild.isMember(player))
        {
            sender.sendMessage(Component.text("This player is already in a party.").color(NamedTextColor.RED));
            return;
        }

        User user = User.getInstance(player);

        if (user.getGuild() != null)
        {
            user.getGuild().quit(user);
        }

        guild.join(user);
    }

    @Override
    public ArrayList<String> onTabComplete(CommandSender sender, Command command, String label, String[] args)
    {
        ArrayList<String> suggest = new ArrayList<>();

        if (args.length == 1)
        {
            Guild.getInstances().forEach(g -> suggest.add(g.getId().toString()));
        }

        if (args.length == 2)
        {
            Bukkit.getOnlinePlayers().forEach(p -> suggest.add(p.getName()));
        }

        return suggest;
    }
}
