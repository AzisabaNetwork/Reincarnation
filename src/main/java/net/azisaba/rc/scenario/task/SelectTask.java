package net.azisaba.rc.scenario.task;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import net.azisaba.rc.scenario.Scenario;
import net.azisaba.rc.scenario.hologram.Hologram;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.regex.Pattern;

public class SelectTask extends ScenarioTask
{
    public static Pattern getPattern()
    {
        return Pattern.compile("\\$Select");
    }

    private Hologram hologram;
    private final Option jumpOption;
    private final Option shiftOption;

    public SelectTask(String name, Scenario scenario)
    {
        super(name, scenario);

        this.jumpOption = new Option(this.parameters.getString("Jump.Name"), this.parameters.getStringList("Jump.Script"));
        this.shiftOption = new Option(this.parameters.getString("Shift.Name"), this.parameters.getStringList("Shift.Script"));
    }

    public void onPlayerJump(PlayerJumpEvent event)
    {
        this.jumpOption.script.forEach(s -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s.replace("${player}", event.getPlayer().getName())));

        this.scenario.next();
        this.hologram.despawn(this.scenario.getPlayer());
    }

    public void onPlayerShift(PlayerToggleSneakEvent event)
    {
        this.shiftOption.script.forEach(s -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s.replace("${player}", event.getPlayer().getName())));

        this.scenario.next();
        this.hologram.despawn(this.scenario.getPlayer());
    }

    @Override
    public void run(Player player)
    {
        TextComponent info = new TextComponent("Jump または Shift");
        info.setColor(ChatColor.GREEN);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, info);

        this.hologram = new Hologram(String.format("Jump: %s", this.jumpOption.name), String.format("Shift: %s", this.shiftOption.name));
        Vector direction = player.getLocation().getDirection().normalize().multiply(2);
        this.hologram.spawn(player.getLocation().add(direction), player);
    }

    record Option(String name, List<String> script)
    {

    }
}
