package net.azisaba.rc.command.skill.party;

import net.azisaba.rc.command.skill.RcCommandSkill;
import net.azisaba.rc.quest.Party;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PartyKickSkill extends RcCommandSkill
{

    public PartyKickSkill()
    {
        super("party:kick");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (args.length != 2)
        {
            sender.sendMessage(Component.text("Correct syntax: /rc party:kick <party> <player>").color(NamedTextColor.RED));
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

        Party party = Party.getInstance(args[0]);
        Player player = Bukkit.getPlayer(args[1]);

        if (! party.isMember(player))
        {
            sender.sendMessage(Component.text("This player is not a party member.").color(NamedTextColor.RED));
            return true;
        }

        player.sendMessage(Component.text("Party は解散されました…").color(NamedTextColor.RED));
        party.quit(player);
        party.broadcast(Component.text(party.getLeader().getName() + " が " + player.getName() + " を Party から追放しました").color(NamedTextColor.RED));
        return true;
    }
}
