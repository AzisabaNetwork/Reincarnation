package net.azisaba.rc.command.skill.party;

import net.azisaba.rc.command.skill.IRcCommandSkill;
import net.azisaba.rc.quest.Party;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class PartyCloseSkill implements IRcCommandSkill
{

    @Override
    public String getName()
    {
        return "party:close";
    }

    @Override
    public boolean isOPCommand()
    {
        return true;
    }

    @Override
    public void onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (args.length != 1)
        {
            sender.sendMessage(Component.text("Correct syntax: /rc ui:close <party>").color(NamedTextColor.RED));
            return;
        }

        if (Party.getInstance(args[0]) == null)
        {
            sender.sendMessage(Component.text(args[0] + " is an unknown party.").color(NamedTextColor.RED));
            return;
        }

        Party party = Party.getInstance(args[0]);
        party.getLeader().closeInventory();
        party.getLeader().sendMessage(Component.text("Party を終了しました…").color(NamedTextColor.GREEN));
        party.close();
    }

    @Override
    public ArrayList<String> onTabComplete(CommandSender sender, Command command, String label, String[] args)
    {
        return null;
    }
}
