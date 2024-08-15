package net.azisaba.rc.command.skill.guild;

import net.azisaba.rc.command.skill.IRcCommandSkill;
import net.azisaba.rc.guild.Guild;
import net.azisaba.rc.user.User;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GuildQuitSkill implements IRcCommandSkill
{

    @Override
    public String getName()
    {
        return "guild:quit";
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
            sender.sendMessage(Component.text("Correct syntax: /rc guild:quit <player>").color(NamedTextColor.RED));
            return;
        }

        if (Bukkit.getServer().getPlayerExact(args[0]) == null)
        {
            sender.sendMessage(Component.text(args[0] + " is currently offline.").color(NamedTextColor.RED));
            return;
        }

        Player player = Bukkit.getPlayer(args[0]);
        User user = User.getInstance(player);

        if (user.getGuild() == null)
        {
            sender.sendMessage(Component.text("This player is not in a guild.").color(NamedTextColor.RED));
            return;
        }

        player.closeInventory();

        Guild guild = user.getGuild();
        guild.quit(user);
    }

    @Override
    public ArrayList<String> onTabComplete(CommandSender sender, Command command, String label, String[] args)
    {
        ArrayList<String> suggest = new ArrayList<>();

        if (args.length == 1)
        {
            Bukkit.getOnlinePlayers().forEach(p -> suggest.add(p.getName()));
        }

        return suggest;
    }
}
