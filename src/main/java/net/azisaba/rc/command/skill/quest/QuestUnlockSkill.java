package net.azisaba.rc.command.skill.quest;

import net.azisaba.rc.command.skill.IRcCommandSkill;
import net.azisaba.rc.quest.QuestEngine;
import net.azisaba.rc.user.User;
import net.azisaba.rc.util.PartyUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class QuestUnlockSkill implements IRcCommandSkill
{

    @Override
    public String getName()
    {
        return "quest:unlock";
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
            sender.sendMessage(Component.text("Correct syntax: /rc quest:unlock <quest> <player>").color(NamedTextColor.RED));
            return;
        }

        if (QuestEngine.getInstance(args[0]) == null)
        {
            sender.sendMessage(Component.text(args[0] + " is an unknown quest.").color(NamedTextColor.RED));
            return;
        }

        if (Bukkit.getServer().getPlayerExact(args[1]) == null)
        {
            sender.sendMessage(Component.text(args[1] + " is currently offline.").color(NamedTextColor.RED));
            return;
        }

        Player player = Bukkit.getPlayer(args[0]);

        if (! PartyUtil.isPartyPlayer(player))
        {
            sender.sendMessage(Component.text("This player is not a party member.").color(NamedTextColor.RED));
            return;
        }

        User user = User.getInstance(player);
        QuestEngine quest = QuestEngine.getInstance(args[0]);

        if (user.isUnlocked(quest))
        {
            sender.sendMessage(Component.text("This player has already unlocked this quest.").color(NamedTextColor.RED));
            return;
        }

        user.unlock(quest);

        Component lore = Component.text("");

        for (String row : quest.getLore())
        {
            lore = lore.append(LegacyComponentSerializer.legacyAmpersand().deserialize(row + "\n"));
        }

        player.sendMessage(Component.text("クエスト ").color(NamedTextColor.YELLOW)
                .append(Component.text(quest.getDisplay()).color(NamedTextColor.AQUA).hoverEvent(HoverEvent.showText(lore)))
                .append(Component.text(" をアンロックしました！").color(NamedTextColor.YELLOW)));
    }

    @Override
    public ArrayList<String> onTabComplete(CommandSender sender, Command command, String label, String[] args)
    {
        return null;
    }
}
