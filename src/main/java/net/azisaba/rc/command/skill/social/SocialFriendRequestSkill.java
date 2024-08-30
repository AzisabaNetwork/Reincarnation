package net.azisaba.rc.command.skill.social;

import net.azisaba.rc.command.skill.ICommandSkill;
import net.azisaba.rc.ui.CLI;
import net.azisaba.rc.user.User;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class SocialFriendRequestSkill implements ICommandSkill
{
    @Override
    public String getName()
    {
        return "social:friend-request";
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
            sender.sendMessage(Component.text("Correct syntax: /rc social:friend-request <from> <to>").color(NamedTextColor.RED));
            return;
        }

        if (Bukkit.getServer().getPlayerExact(args[0]) == null || Bukkit.getServer().getPlayerExact(args[1]) == null)
        {
            sender.sendMessage(Component.text("Player1 or 2 is currently offline.").color(NamedTextColor.RED));
            return;
        }

        final Player from = Bukkit.getPlayer(args[0]);
        from.closeInventory();

        final Player to = Bukkit.getPlayer(args[1]);

        final User fromUser = User.getInstance(from);
        fromUser.getFriendRequests().add(User.getInstance(to));

        to.sendMessage(Component.text(CLI.SEPARATOR).color(NamedTextColor.BLUE));
        to.sendMessage(from.displayName().append(Component.text(" から Friend 申請が届きました！").color(NamedTextColor.YELLOW)));
        to.sendMessage(Component.text("こちらをクリックして承認！").color(NamedTextColor.GOLD).clickEvent(ClickEvent.runCommand(String.format("/rc social:friend %s %s", from.getName(), to.getName()))).hoverEvent(HoverEvent.showText(Component.text("クリックして、申請を承認します。").color(NamedTextColor.GRAY))));
        to.sendMessage(Component.text(CLI.SEPARATOR).color(NamedTextColor.BLUE));

        from.sendMessage(Component.text(CLI.SEPARATOR).color(NamedTextColor.BLUE));
        from.sendMessage(to.displayName().append(Component.text(" に Friend 申請を送信しました").color(NamedTextColor.YELLOW)));
        from.sendMessage(Component.text(CLI.SEPARATOR).color(NamedTextColor.BLUE));
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
            Bukkit.getOnlinePlayers().forEach(p -> suggest.add(p.getName()));
        }

        return suggest;
    }
}
