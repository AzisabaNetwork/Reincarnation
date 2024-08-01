package net.azisaba.rc.command.skill.guild;

import net.azisaba.rc.command.skill.AbstractTypingSkill;
import net.azisaba.rc.guild.Guild;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuildRenameSkill extends AbstractTypingSkill
{
    private Guild guild;

    public GuildRenameSkill()
    {
        super("guild:rename");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (args.length != 2)
        {
            sender.sendMessage(Component.text("Correct syntax: /rc guild:rename <guild> <player>").color(NamedTextColor.RED));
            return true;
        }

        if (Guild.getInstance(args[0]) == null)
        {
            sender.sendMessage(Component.text(args[0] + " is an unknown guild.").color(NamedTextColor.RED));
            return true;
        }

        if (Bukkit.getServer().getPlayerExact(args[1]) == null)
        {
            sender.sendMessage(Component.text(args[1] + " is currently offline.").color(NamedTextColor.RED));
            return true;
        }

        this.guild = Guild.getInstance(args[0]);
        this.player = Bukkit.getPlayer(args[1]);
        this.player.closeInventory();
        return super.onCommand(sender, command, label, args);
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
