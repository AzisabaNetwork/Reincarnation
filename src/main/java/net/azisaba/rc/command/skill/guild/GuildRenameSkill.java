package net.azisaba.rc.command.skill.guild;

import net.azisaba.rc.command.skill.TypingSkill;
import net.azisaba.rc.guild.Guild;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class GuildRenameSkill extends TypingSkill
{
    private Guild guild;

    @Override
    public String getName()
    {
        return "guild:rename";
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
            sender.sendMessage(Component.text("Correct syntax: /rc guild:rename <guild> <player>").color(NamedTextColor.RED));
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

    @Override
    public void enableTyping(Player player)
    {
        super.enableTyping(player);
        player.sendMessage(Component.text("Guild の新しい名前を送信してください:").color(NamedTextColor.GREEN));
    }

    @Override
    public void onTyped(String string)
    {
        if (12 < string.length())
        {
            this.player.sendMessage(Component.text("Guile 名は12文字以下にする必要があります。").color(NamedTextColor.RED));
            return;
        }

        if (Guild.getInstances().stream().anyMatch(g -> g.getName().equals(string)))
        {
            this.player.sendMessage(Component.text("この Guile 名はすでに使用されています。").color(NamedTextColor.RED));
            return;
        }

        this.guild.setName(string);
        this.player.sendMessage(Component.text("Guild 名を変更しました。").color(NamedTextColor.GREEN));
    }
}
