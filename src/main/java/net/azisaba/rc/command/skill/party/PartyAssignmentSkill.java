package net.azisaba.rc.command.skill.party;

import net.azisaba.rc.command.skill.RcCommandSkill;
import net.azisaba.rc.quest.Party;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PartyAssignmentSkill extends RcCommandSkill
{

    public PartyAssignmentSkill()
    {
        super("party:assignment");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (args.length != 2)
        {
            sender.sendMessage(Component.text("Correct syntax: /rc party:assignment <party> <player>").color(NamedTextColor.RED));
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

        party.getLeader().closeInventory();
        party.getLeader().sendMessage(Component.text("Party リーダーを変更しています…").color(NamedTextColor.GREEN));
        party.setLeader(player);
        party.broadcast(Component.text("この Party は " + player.getName() + " に譲渡されました").color(NamedTextColor.RED));
        player.sendMessage(Component.text("あなたは Party リーダーに選択されました！").color(NamedTextColor.GREEN));
        return true;
    }
}
