package net.azisaba.rc.scenario.task;

import net.azisaba.rc.scenario.Scenario;
import net.azisaba.rc.scenario.hologram.Hologram;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.regex.Pattern;

public class SelectTask extends ScenarioTask
{
    public static Pattern getPattern()
    {
        return Pattern.compile("\\$Select");
    }

    private Hologram hologram;
    private final Option rightOption;
    private final Option leftOption;

    public SelectTask(String name, Scenario scenario)
    {
        super(name, scenario);

        this.rightOption = new Option(this.parameters.getString("Right.Name"), this.parameters.getString("Right.Command"));
        this.leftOption = new Option(this.parameters.getString("Left.Name"), this.parameters.getString("Left.Command"));
    }

    @Override
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        if (event.getAction().toString().startsWith("RIGHT_CLICK"))
        {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.rightOption.command);
        }
        else
        {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.leftOption.command);
        }

        this.scenario.next();
        this.hologram.despawn(this.scenario.getPlayer());
    }

    @Override
    public void run(Player player)
    {
        TextComponent info = new TextComponent("ホログラムを右クリック / 左クリック");
        info.setColor(ChatColor.GREEN);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, info);

        this.hologram = new Hologram(String.format("R Click: %s", this.rightOption.name), String.format("L Click: %s", this.leftOption.name));
        Vector direction = player.getLocation().getDirection().normalize().multiply(6);
        this.hologram.spawn(player.getLocation().add(direction), player);
    }

    record Option(String name, String command)
    {

    }
}
