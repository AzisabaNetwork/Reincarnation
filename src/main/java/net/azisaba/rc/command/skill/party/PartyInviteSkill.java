package net.azisaba.rc.command.skill.party;

import net.azisaba.rc.command.skill.IRcCommandSkill;
import net.azisaba.rc.quest.Party;
import net.azisaba.rc.ui.CLI;
import net.azisaba.rc.util.PartyUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class PartyInviteSkill implements IRcCommandSkill
{

    @Override
    public String getName()
    {
        return "party:invite";
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
            sender.sendMessage(Component.text("Correct syntax: /rc party:invite <party> <player>").color(NamedTextColor.RED));
            return;
        }

        if (Party.getInstance(args[0]) == null)
        {
            sender.sendMessage(Component.text(args[0] + " is an unknown party.").color(NamedTextColor.RED));
            return;
        }

        if (Bukkit.getServer().getPlayerExact(args[1]) == null)
        {
            sender.sendMessage(Component.text(args[1] + " is currently offline.").color(NamedTextColor.RED));
            return;
        }

        Player player = Bukkit.getPlayer(args[1]);
        Party party = Party.getInstance(args[0]);

        if (PartyUtil.isPartyPlayer(player))
        {
            sender.sendMessage(Component.text("This player is already in a party.").color(NamedTextColor.RED));
            return;
        }

        player.sendMessage(Component.text(CLI.SEPARATOR).color(NamedTextColor.BLUE));
        player.sendMessage(party.getLeader().displayName()
                .append(Component.text(" があなたを Party に招待しました！").color(NamedTextColor.YELLOW)));
        player.sendMessage(Component.text("こちらをクリックして参加！").color(NamedTextColor.GOLD).clickEvent(ClickEvent.runCommand("/rc party:join " + args[0] + " " + player.getName())).hoverEvent(HoverEvent.showText(Component.text("クリックして、招待を承認します。"))));
        player.sendMessage(Component.text(CLI.SEPARATOR).color(NamedTextColor.BLUE));

        party.getMembers().forEach(m -> m.sendMessage(Component.text(CLI.SEPARATOR).color(NamedTextColor.BLUE)));
        party.getMembers().forEach(m -> m.sendMessage(party.getLeader().displayName()
                .append(Component.text(" が ").color(NamedTextColor.YELLOW))
                .append(player.displayName())
                .append(Component.text(" を Party に招待しました！").color(NamedTextColor.YELLOW))));
        party.getMembers().forEach(m -> m.sendMessage(Component.text(CLI.SEPARATOR).color(NamedTextColor.BLUE)));

        party.getLeader().closeInventory();
    }

    @Override
    public ArrayList<String> onTabComplete(CommandSender sender, Command command, String label, String[] args)
    {
        return null;
    }
}
