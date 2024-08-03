package net.azisaba.rc.command.skill.ui;

import net.azisaba.rc.command.skill.RcCommandSkill;
import net.azisaba.rc.ui.inventory.AbstractInventoryUI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public abstract class UIOpenSkill extends RcCommandSkill
{
    protected final Class<? extends AbstractInventoryUI> clazz;

    public UIOpenSkill(String name, Class<? extends AbstractInventoryUI> clazz)
    {
        super(name);

        this.clazz = clazz;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (args.length != 1)
        {
            sender.sendMessage(Component.text(String.format("Correct syntax: /rc %s <player>", this.getName())).color(NamedTextColor.RED));
            return true;
        }

        if (Bukkit.getServer().getPlayerExact(args[0]) == null)
        {
            sender.sendMessage(Component.text(args[0] + " is currently offline.").color(NamedTextColor.RED));
            return true;
        }

        Player player = Bukkit.getPlayer(args[0]);

        try
        {
            this.clazz.getConstructor(Player.class).newInstance(player);
        }
        catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e)
        {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public ArrayList<String> onTabComplete(CommandSender sender, Command command, String label, String[] args)
    {
        ArrayList<String> list = new ArrayList<>();

        if (args.length == 1)
        {
            Bukkit.getOnlinePlayers().forEach(p -> list.add(p.getName()));
        }

        return list;
    }
}
