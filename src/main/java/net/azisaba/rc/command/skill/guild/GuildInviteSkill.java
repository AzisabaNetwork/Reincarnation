package net.azisaba.rc.command.skill.guild;

import net.azisaba.rc.command.skill.RcCommandSkill;
import net.azisaba.rc.guild.Guild;
import net.azisaba.rc.ui.CLI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuildInviteSkill extends RcCommandSkill
{

    public GuildInviteSkill()
    {
        super("guild:invite");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (args.length != 2)
        {
            sender.sendMessage(Component.text("Correct syntax: /rc guild:invite <guild> <player>").color(NamedTextColor.RED));
            return true;
        }

        if (Guild.getInstance(args[0]) == null)
        {
            sender.sendMessage(Component.text(args[0] + " is an unknown guild.").color(NamedTextColor.RED));
            return true;
        }

        if (Bukkit.getServer().getPlayerExact(args[1]) == null)
        {
            sender.sendMessage(Component.text(args[1] + " is currently offline.").color(NamedTextColor.RED));
            return true;
        }

        Player player = Bukkit.getPlayer(args[1]);
        Guild guild = Guild.getInstance(args[0]);

        if (guild.isMember(player))
        {
            sender.sendMessage(Component.text("This player is already in a guild.").color(NamedTextColor.RED));
            return true;
        }

        player.sendMessage(Component.text(CLI.SEPARATOR).color(NamedTextColor.BLUE));
        player.sendMessage(guild.getMaster().getRankedName()
                .append(Component.text(" があなたを Guild に招待しました！").color(NamedTextColor.YELLOW)));
        player.sendMessage(Component.text("こちらをクリックして参加！").color(NamedTextColor.GOLD).clickEvent(ClickEvent.runCommand("/rc guild:join " + args[0] + " " + player.getName())).hoverEvent(HoverEvent.showText(Component.text("クリックして、招待を承認します。"))));
        player.sendMessage(Component.text(CLI.SEPARATOR).color(NamedTextColor.BLUE));

        guild.getMembers().forEach(m -> m.sendMessage(Component.text(CLI.SEPARATOR).color(NamedTextColor.BLUE)));
        guild.getMembers().forEach(m -> m.sendMessage(guild.getMaster().getRankedName()
                .append(Component.text(" が ").color(NamedTextColor.YELLOW))
                .append(player.displayName())
                .append(Component.text(" を Guild に招待しました！").color(NamedTextColor.YELLOW))));
        guild.getMembers().forEach(m -> m.sendMessage(Component.text(CLI.SEPARATOR).color(NamedTextColor.BLUE)));
        return true;
    }
}
