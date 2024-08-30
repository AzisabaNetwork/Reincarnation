package net.azisaba.rc.command.skill.quest;

import net.azisaba.rc.command.skill.ICommandSkill;
import net.azisaba.rc.quest.QuestEngine;
import net.azisaba.rc.user.User;
import net.azisaba.rc.util.PartyUtility;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class QuestUnlockSkill implements ICommandSkill
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
            sender.sendMessage(Component.text("Correct syntax: /rc quest:unlock <quest engine> <player>").color(NamedTextColor.RED));
            return;
        }

        if (QuestEngine.getInstance(args[0]) == null)
        {
            sender.sendMessage(Component.text(args[0] + " is an unknown quest engine.").color(NamedTextColor.RED));
            return;
        }

        if (Bukkit.getServer().getPlayerExact(args[1]) == null)
        {
            sender.sendMessage(Component.text(args[1] + " is currently offline.").color(NamedTextColor.RED));
            return;
        }

        Player player = Bukkit.getPlayer(args[1]);

        if (! PartyUtility.isPartyPlayer(player))
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
        ArrayList<String> suggest = new ArrayList<>();

        if (args.length == 1)
        {
            QuestEngine.getInstances().forEach(e -> suggest.add(e.getId()));
        }

        if (args.length == 2)
        {
            Bukkit.getOnlinePlayers().forEach(p -> suggest.add(p.getName()));
        }

        return suggest;
    }
}
