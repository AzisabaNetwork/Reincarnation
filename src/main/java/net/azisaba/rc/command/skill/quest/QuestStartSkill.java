package net.azisaba.rc.command.skill.quest;

import net.azisaba.rc.command.skill.RcCommandSkill;
import net.azisaba.rc.quest.Party;
import net.azisaba.rc.quest.Quest;
import net.azisaba.rc.quest.QuestEngine;
import net.azisaba.rc.util.PartyUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class QuestStartSkill extends RcCommandSkill
{

    public QuestStartSkill()
    {
        super("quest:start");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (args.length != 2)
        {
            sender.sendMessage(Component.text("Correct syntax: /rc quest:start <quest> <player>").color(NamedTextColor.RED));
            return true;
        }

        if (QuestEngine.getInstance(args[0]) == null)
        {
            sender.sendMessage(Component.text(args[0] + " is an unknown quest.").color(NamedTextColor.RED));
            return true;
        }

        if (Bukkit.getServer().getPlayerExact(args[1]) == null)
        {
            sender.sendMessage(Component.text(args[1] + " is currently offline.").color(NamedTextColor.RED));
            return true;
        }

        Player player = Bukkit.getPlayer(args[1]);
        Party party = PartyUtil.isPartyPlayer(player) ? PartyUtil.getParty(player) : new Party(player);

        if (party.getLeader() != player)
        {
            player.sendMessage(Component.text("クエストを開始するには、Party リーダーである必要があります。").color(NamedTextColor.RED));
            return true;
        }

        player.closeInventory();
        new Quest(QuestEngine.getInstance(args[0]), party);
        return true;
    }
}
