package net.azisaba.rc.command.skill;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Random;

public abstract class AbstractPowerSkill extends AbstractTypingSkill
{
    protected String confirmCode;
    protected int length = 6;

    @Override
    public boolean isOPCommand()
    {
        return true;
    }

    @Override
    public void onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        super.onCommand(sender, command, label, args);
    }

    @Override
    public ArrayList<String> onTabComplete(CommandSender sender, Command command, String label, String[] args)
    {
        return null;
    }

    @Override
    public void enableTyping(Player player)
    {
        this.confirmCode = this.getConfirmCode(this.length);

        player.addScoreboardTag("rc.typing");
        player.sendMessage(Component.text(String.format("確認: %s をチャットに送信してください。", this.confirmCode)).color(NamedTextColor.RED));
    }

    @Override
    public void onTyped(String string)
    {
        super.onTyped(string);

        if (string.equals(this.confirmCode))
        {
            this.onAllow();
        }
        else
        {
            this.onForbid();
        }
    }

    public void onAllow()
    {

    }

    public void onForbid()
    {

    }

    public String getConfirmCode(int length)
    {
        if (length < 1)
        {
            length = 1;
        }

        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i ++)
        {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }

        return sb.toString();
    }
}
