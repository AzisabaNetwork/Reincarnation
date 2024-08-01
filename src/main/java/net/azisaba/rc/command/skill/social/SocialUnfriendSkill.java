package net.azisaba.rc.command.skill.social;

import net.azisaba.rc.command.skill.RcCommandSkill;
import net.azisaba.rc.user.User;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SocialUnfriendSkill extends RcCommandSkill
{

    public SocialUnfriendSkill()
    {
        super("social:unfriend");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (args.length != 2)
        {
            sender.sendMessage(Component.text("Correct syntax: /rc social:unfriend <player> <friend>").color(NamedTextColor.RED));
            return true;
        }

        if (Bukkit.getServer().getPlayerExact(args[0]) == null)
        {
            sender.sendMessage(Component.text(args[0] + " is currently offline.").color(NamedTextColor.RED));
            return true;
        }

        Player player = Bukkit.getPlayer(args[0]);
        player.closeInventory();
        User user = User.getInstance(player);

        OfflinePlayer friendPlayer = Bukkit.getServer().getOfflinePlayer(args[1]);
        User friendUser = User.getInstance(friendPlayer.getUniqueId().toString());

        user.unfriend(friendUser);
        player.sendMessage(Component.text(friendPlayer.getName()).color(NamedTextColor.GRAY).append(Component.text(" を Friend から削除しました").color(NamedTextColor.YELLOW)));
        return true;
    }
}
