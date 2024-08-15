package net.azisaba.rc.command.skill.social;

import net.azisaba.rc.command.skill.AbstractTypingSkill;
import net.azisaba.rc.user.User;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public abstract class AbstractSocialLinkSkill extends AbstractTypingSkill
{
    protected User user;

    public String getServiceName()
    {
        return null;
    }

    @Override
    public boolean isOPCommand()
    {
        return true;
    }

    public boolean isValidHandle(String handle)
    {
        return false;
    }

    public void onLink(String handle)
    {

    }

    @Override
    public void onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (args.length != 1)
        {
            sender.sendMessage(Component.text(String.format("Correct syntax: /rc %s <player>", this.getName())).color(NamedTextColor.RED));
            return;
        }

        if (Bukkit.getServer().getPlayerExact(args[0]) == null)
        {
            sender.sendMessage(Component.text(args[0] + " is currently offline.").color(NamedTextColor.RED));
            return;
        }

        this.player = Bukkit.getPlayer(args[0]);
        this.player.closeInventory();
        this.user = User.getInstance(this.player);
        super.onCommand(sender, command, label, args);
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

    @Override
    public void enableTyping(Player player)
    {
        super.enableTyping(player);
        player.sendMessage(Component.text(String.format("%s のハンドルを送信してください:", this.getServiceName())).color(NamedTextColor.GREEN));
        player.sendMessage(Component.text("「:」を入力してキャンセルします。").color(NamedTextColor.YELLOW));
    }

    @Override
    public void onTyped(String string)
    {
        if (string.equals(":"))
        {
            this.player.sendMessage(Component.text("この操作をキャンセルしました。").color(NamedTextColor.GREEN));
            return;
        }

        string = string.startsWith("@") ? string.substring(1) : string;

        if (! this.isValidHandle(string))
        {
            this.player.sendMessage(Component.text(String.format("%s で無効なハンドルです。", this.getServiceName())).color(NamedTextColor.RED));
            return;
        }

        this.onLink(string);
        this.player.sendMessage(Component.text(String.format("%s アカウント %s をあなたのプロフィールにリンクしました。", this.getServiceName(), string)).color(NamedTextColor.GREEN));
    }
}
