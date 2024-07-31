package net.azisaba.rc.command.skill.party;

import net.azisaba.rc.command.skill.RcCommandSkill;
import net.azisaba.rc.quest.Party;
import net.azisaba.rc.ui.inventory.menu.PartyUI;
import net.azisaba.rc.util.PartyUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PartyCreateSkill extends RcCommandSkill
{

    public PartyCreateSkill()
    {
        super("party:create");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (args.length != 1)
        {
            sender.sendMessage(Component.text("Correct syntax: /rc party:create <player>").color(NamedTextColor.RED));
            return true;
        }

        if (Bukkit.getServer().getPlayerExact(args[0]) == null)
        {
            sender.sendMessage(Component.text(args[0] + " is currently offline.").color(NamedTextColor.RED));
            return true;
        }

        Player player = Bukkit.getPlayer(args[0]);

        if (PartyUtil.isPartyPlayer(player))
        {
            sender.sendMessage(Component.text(args[0] + " is already at the party.").color(NamedTextColor.RED));
            return true;
        }

        new Party(player);
        new PartyUI(player);
        return true;
    }
}
