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

public class GuildQuitSkill extends RcCommandSkill
{

    public GuildQuitSkill()
    {
        super("guild:quit");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (args.length != 1)
        {
            sender.sendMessage(Component.text("Correct syntax: /rc guild:quit <player>").color(NamedTextColor.RED));
            return true;
        }

        if (Bukkit.getServer().getPlayerExact(args[0]) == null)
        {
            sender.sendMessage(Component.text(args[0] + " is currently offline.").color(NamedTextColor.RED));
            return true;
        }

        Player player = Bukkit.getPlayer(args[0]);
        User user = User.getInstance(player);

        if (user.getGuild() == null)
        {
            sender.sendMessage(Component.text("This player is not in a guild.").color(NamedTextColor.RED));
            return true;
        }

        player.closeInventory();

        Guild guild = user.getGuild();
        guild.quit(user);
        return true;
    }
}
