package net.azisaba.rc.command.skill.quest;

import net.azisaba.rc.command.skill.ICommandSkill;
import net.azisaba.rc.quest.Party;
import net.azisaba.rc.quest.Quest;
import net.azisaba.rc.quest.QuestEngine;
import net.azisaba.rc.util.PartyUtility;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class QuestProgressionSkill implements ICommandSkill
{
    @Override
    public String getName()
    {
        return "quest:progression";
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
            sender.sendMessage(Component.text("Correct syntax: /rc quest:progression <player> <quest engine>").color(NamedTextColor.RED));
            return;
        }

        if (QuestEngine.getInstance(args[1]) == null)
        {
            sender.sendMessage(Component.text(args[0] + " is an unknown quest engine.").color(NamedTextColor.RED));
            return;
        }

        Player player = Bukkit.getPlayer(args[0]);
        Party party = PartyUtility.getParty(player);
        Quest quest = party.getQuest();

        if (quest == null)
        {
            sender.sendMessage(Component.text("This party does not have a quest.").color(NamedTextColor.RED));
            return;
        }

        quest.setProgress(quest.getProgress() + 1);
    }

    @Override
    public ArrayList<String> onTabComplete(CommandSender sender, Command command, String label, String[] args)
    {
        ArrayList<String> suggest = new ArrayList<>();

        if (args.length == 1)
        {
            Bukkit.getOnlinePlayers().forEach(p -> suggest.add(p.getName()));
        }

        if (args.length == 2)
        {
            QuestEngine.getInstances().forEach(e -> suggest.add(e.getId()));
        }

        return suggest;
    }
}
