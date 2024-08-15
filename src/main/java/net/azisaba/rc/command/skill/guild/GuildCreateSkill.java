package net.azisaba.rc.command.skill.guild;

import net.azisaba.rc.command.skill.IRcCommandSkill;
import net.azisaba.rc.guild.Guild;
import net.azisaba.rc.ui.CLI;
import net.azisaba.rc.user.User;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GuildCreateSkill implements IRcCommandSkill
{

    @Override
    public String getName()
    {
        return "guild:create";
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
            sender.sendMessage(Component.text("Correct syntax: /rc guild:create <name> <player>").color(NamedTextColor.RED));
            return;
        }

        if (Bukkit.getServer().getPlayerExact(args[1]) == null)
        {
            sender.sendMessage(Component.text(args[1] + " is currently offline.").color(NamedTextColor.RED));
            return;
        }

        Player player = Bukkit.getPlayer(args[1]);
        User user = User.getInstance(player);

        player.closeInventory();

        if (user.getMoney() < 10000)
        {
            player.sendMessage(Component.text("Guild を作成するのには 10000 RI 必要です。").color(NamedTextColor.RED));
            return;
        }

        if (user.getGuild() != null)
        {
            player.sendMessage(Component.text("あなたは既に Guild を所有しています").color(NamedTextColor.RED));
            return;
        }

        user.setMoney(user.getMoney() - 10000);

        new Guild(args[0], user);
        player.sendMessage(Component.text(CLI.SEPARATOR).color(NamedTextColor.BLUE));
        player.sendMessage("");
        player.sendMessage(Component.text(args[0]).color(NamedTextColor.GRAY).append(Component.text(" として新しい Guild を作成しました！").color(NamedTextColor.YELLOW)));
        player.sendMessage("");
        player.sendMessage(Component.text(CLI.SEPARATOR).color(NamedTextColor.BLUE));
    }

    @Override
    public ArrayList<String> onTabComplete(CommandSender sender, Command command, String label, String[] args)
    {
        ArrayList<String> suggest = new ArrayList<>();

        if (args.length == 1)
        {
            suggest.add("Guild name…");
        }

        if (args.length == 2)
        {
            Bukkit.getOnlinePlayers().forEach(p -> suggest.add(p.getName()));
        }

        return suggest;
    }
}
