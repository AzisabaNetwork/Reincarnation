package net.azisaba.rc.command.skill.social;

import net.azisaba.rc.command.skill.RcCommandSkill;
import net.azisaba.rc.user.User;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SocialFriendSkill extends RcCommandSkill
{

    public SocialFriendSkill()
    {
        super("social:friend");
        this.opcmd = false;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (args.length != 2)
        {
            sender.sendMessage(Component.text("Correct syntax: /rc social:friend <player1> <player2>").color(NamedTextColor.RED));
            return true;
        }

        if (Bukkit.getServer().getPlayerExact(args[0]) == null || Bukkit.getServer().getPlayerExact(args[1]) == null)
        {
            sender.sendMessage(Component.text("Player1 or 2 is currently offline.").color(NamedTextColor.RED));
            return true;
        }

        final Player player1 = Bukkit.getPlayer(args[0]);
        final User user1 = User.getInstance(player1.getUniqueId().toString());

        final Player player2 = Bukkit.getPlayer(args[1]);
        final User user2 = User.getInstance(player2.getUniqueId().toString());

        if ((sender instanceof Player player) && player != player2 && ! sender.isOp())
        {
            sender.sendMessage(Component.text("This syntax violates permission.").color(NamedTextColor.RED));
            return true;
        }

        if (! user1.getFriendRequests().contains(user2) && ! sender.isOp())
        {
            sender.sendMessage(Component.text("You must submit an application first.").color(NamedTextColor.RED));
            return true;
        }

        if (user1.isFriend(user2))
        {
            sender.sendMessage(Component.text("You are already friends with this player.").color(NamedTextColor.RED));
            return true;
        }

        if (user1 == user2)
        {
            player1.sendMessage(Component.text("自分自身に Friend 申請を送信することはできません。").color(NamedTextColor.RED));
        }

        user1.friend(user2);

        player1.sendMessage(player2.displayName().append(Component.text(" と Friend になりました！").color(NamedTextColor.YELLOW)));
        player2.sendMessage(player1.displayName().append(Component.text(" と Friend になりました！").color(NamedTextColor.YELLOW)));
        return true;
    }
}
