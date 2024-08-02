package net.azisaba.rc.scenario.task;

import net.azisaba.rc.scenario.Scenario;
import net.azisaba.rc.scenario.hologram.Hologram;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class HologramTask extends ScenarioTask
{
    public static Pattern getPattern()
    {
        return Pattern.compile("^[^$].*");
    }

    private Hologram hologram;

    public HologramTask(String name, Scenario scenario)
    {
        super(name, scenario);
    }

    @Override
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        if (event.getAction().toString().startsWith("LEFT"))
        {
            this.hologram.despawn(event.getPlayer());
            this.scenario.next();
        }
    }

    @Override
    public void run(Player player)
    {
        TextComponent info = new TextComponent("左クリックで次に進みます");
        info.setColor(ChatColor.GREEN);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, info);

        ArrayList<String> rows = new ArrayList<>();
        rows.add("§l" + this.name);
        rows.addAll(this.scenario.getEngine().getConfig().getStringList(this.name));

        this.hologram = new Hologram(rows.toArray(String[]::new));
        Vector direction = player.getLocation().getDirection().normalize().multiply(6);
        this.hologram.spawn(player.getLocation().add(direction), player);
    }
}
