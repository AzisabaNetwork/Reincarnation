package net.azisaba.rc.command.skill.party;

import net.azisaba.rc.command.skill.RcCommandSkill;
import net.azisaba.rc.quest.Party;
import net.azisaba.rc.util.PartyUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PartyJoinSkill extends RcCommandSkill
{

    public PartyJoinSkill()
    {
        super("party:join");
        this.opcmd = false;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (args.length != 2)
        {
            sender.sendMessage(Component.text("Correct syntax: /rc party:join <party> <player>").color(NamedTextColor.RED));
            return true;
        }

        if (Party.getInstance(args[0]) == null)
        {
            sender.sendMessage(Component.text(args[0] + " is an unknown party.").color(NamedTextColor.RED));
            return true;
        }

        if (Bukkit.getServer().getPlayerExact(args[1]) == null)
        {
            sender.sendMessage(Component.text(args[1] + " is currently offline.").color(NamedTextColor.RED));
            return true;
        }

        Player player = Bukkit.getPlayer(args[1]);

        if (PartyUtil.isPartyPlayer(player))
        {
            sender.sendMessage(Component.text("This player is already in a party.").color(NamedTextColor.RED));
            return true;
        }

        Party party = Party.getInstance(args[0]);
        party.join(player);
        return true;
    }
}
