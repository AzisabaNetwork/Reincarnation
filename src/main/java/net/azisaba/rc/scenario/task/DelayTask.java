package net.azisaba.rc.scenario.task;

import net.azisaba.rc.Reincarnation;
import net.azisaba.rc.scenario.Scenario;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.regex.Pattern;

public class DelayTask extends ScenarioTask
{
    public static Pattern getPattern()
    {
        return Pattern.compile("\\$Delay");
    }

    private final int delay;

    public DelayTask(String name, Scenario scenario)
    {
        super(name, scenario);

        this.delay = this.scenario.getEngine().getConfig().getInt(this.name);
    }

    @Override
    public void run(Player player)
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                scenario.next();
            }
        }.runTaskLater(Reincarnation.getPlugin(), this.delay);
    }
}
