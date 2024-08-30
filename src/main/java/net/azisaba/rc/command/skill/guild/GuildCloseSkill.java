package net.azisaba.rc.command.skill.guild;

import net.azisaba.rc.command.skill.PowerSkill;
import net.azisaba.rc.guild.Guild;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class GuildCloseSkill extends PowerSkill
{
    private Guild guild;

    @Override
    public String getName()
    {
        return "guild:close";
    }

    @Override
    public void onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (args.length != 2)
        {
            sender.sendMessage(Component.text("Correct syntax: /rc guild:close <guild> <player>").color(NamedTextColor.RED));
            return;
        }

        if (Guild.getInstance(UUID.fromString(args[0])) == null)
        {
            sender.sendMessage(Component.text(args[0] + " is an unknown guild.").color(NamedTextColor.RED));
            return;
        }

        if (Bukkit.getServer().getPlayerExact(args[1]) == null)
        {
            sender.sendMessage(Component.text(args[1] + " is currently offline.").color(NamedTextColor.RED));
            return;
        }

        this.guild = Guild.getInstance(UUID.fromString(args[0]));
        this.player = Bukkit.getPlayer(args[1]);
        this.player.closeInventory();
        super.onCommand(sender, command, label, args);
    }

    @Override
    public void enableTyping(Player player)
    {
        super.enableTyping(player);
        player.sendMessage(Component.text("注意: この操作は取り消せません！").color(NamedTextColor.YELLOW));
    }

    @Override
    public void onAllow()
    {
        this.player.sendMessage(Component.text("Guild を解散しています…").color(NamedTextColor.RED));

        this.guild.close();
    }

    @Override
    public void onForbid()
    {
        this.player.sendMessage(Component.text("Guild の解散をキャンセルしました").color(NamedTextColor.GREEN));
    }

    @Override
    public ArrayList<String> onTabComplete(CommandSender sender, Command command, String label, String[] args)
    {
        ArrayList<String> suggest = new ArrayList<>();

        if (args.length == 1)
        {
            Guild.getInstances().forEach(g -> suggest.add(g.getId().toString()));
        }

        if (args.length == 2)
        {
            Bukkit.getOnlinePlayers().forEach(p -> suggest.add(p.getName()));
        }

        return suggest;
    }
}
