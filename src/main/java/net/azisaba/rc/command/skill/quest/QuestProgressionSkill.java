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

public class QuestProgressionSkill extends RcCommandSkill
{

    public QuestProgressionSkill()
    {
        super("quest:progression");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (args.length != 2)
        {
            sender.sendMessage(Component.text("Correct syntax: /rc quest:progression <player> <quest engine>").color(NamedTextColor.RED));
            return true;
        }

        if (QuestEngine.getInstance(args[1]) == null)
        {
            sender.sendMessage(Component.text(args[0] + " is an unknown quest engine.").color(NamedTextColor.RED));
            return true;
        }

        Player player = Bukkit.getPlayer(args[0]);
        Party party = PartyUtil.getParty(player);
        Quest quest = party.getQuest();

        if (quest == null)
        {
            sender.sendMessage(Component.text("This party does not have a quest.").color(NamedTextColor.RED));
            return true;
        }

        quest.setProgress(quest.getProgress() + 1);
        return true;
    }
}
