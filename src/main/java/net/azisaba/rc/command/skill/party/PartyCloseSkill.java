package net.azisaba.rc.command.skill.party;

import net.azisaba.rc.command.skill.RcCommandSkill;
import net.azisaba.rc.quest.Party;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class PartyCloseSkill extends RcCommandSkill
{

    public PartyCloseSkill()
    {
        super("party:close");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (args.length != 1)
        {
            sender.sendMessage(Component.text("Correct syntax: /rc ui:close <party>").color(NamedTextColor.RED));
            return true;
        }

        if (Party.getInstance(args[0]) == null)
        {
            sender.sendMessage(Component.text(args[0] + " is an unknown party.").color(NamedTextColor.RED));
            return true;
        }

        Party party = Party.getInstance(args[0]);
        party.getLeader().closeInventory();
        party.getLeader().sendMessage(Component.text("Party を終了しました…").color(NamedTextColor.GREEN));
        party.close();
        return true;
    }
}
