package net.azisaba.rc.command.skill.guild;

import net.azisaba.rc.command.skill.RcCommandSkill;
import net.azisaba.rc.guild.Guild;
import net.azisaba.rc.user.User;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuildCreateSkill extends RcCommandSkill
{

    public GuildCreateSkill()
    {
        super("guild:create");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (args.length != 2)
        {
            sender.sendMessage(Component.text("Correct syntax: /rc guild:create <name> <player>").color(NamedTextColor.RED));
            return true;
        }

        if (Bukkit.getServer().getPlayerExact(args[1]) == null)
        {
            sender.sendMessage(Component.text(args[1] + " is currently offline.").color(NamedTextColor.RED));
            return true;
        }

        Player player = Bukkit.getPlayer(args[1]);
        User user = User.getInstance(player);

        if (user.getGuild() != null)
        {
            player.sendMessage(Component.text("あなたは既に Guild を所有しています").color(NamedTextColor.RED));
            return true;
        }

        new Guild(args[0], user);
        return true;
    }
}
