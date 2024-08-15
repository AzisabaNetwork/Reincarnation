package net.azisaba.rc.command.skill.party;

import net.azisaba.rc.command.skill.IRcCommandSkill;
import net.azisaba.rc.quest.Party;
import net.azisaba.rc.util.PartyUtility;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class PartyJoinSkill implements IRcCommandSkill
{

    @Override
    public String getName()
    {
        return "party:join";
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
            sender.sendMessage(Component.text("Correct syntax: /rc party:join <party> <player>").color(NamedTextColor.RED));
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

        if (PartyUtility.isPartyPlayer(player))
        {
            sender.sendMessage(Component.text("This player is already in a party.").color(NamedTextColor.RED));
            return;
        }

        Party party = Party.getInstance(args[0]);
        party.join(player);
    }

    @Override
    public ArrayList<String> onTabComplete(CommandSender sender, Command command, String label, String[] args)
    {
        ArrayList<String> suggest = new ArrayList<>();

        if (args.length == 1)
        {
            Party.getInstances().forEach(p -> suggest.add(p.getId()));
        }

        if (args.length == 2)
        {
            Bukkit.getOnlinePlayers().forEach(p -> suggest.add(p.getName()));
        }

        return suggest;
    }
}
