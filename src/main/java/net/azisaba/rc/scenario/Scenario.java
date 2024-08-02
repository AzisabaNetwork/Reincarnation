package net.azisaba.rc.scenario;

import net.azisaba.rc.scenario.task.ScenarioTask;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;

public class Scenario
{
    private static final ArrayList<Scenario> instances = new ArrayList<>();

    public static Scenario getInstance(Player player)
    {
        ArrayList<Scenario> filteredInstances = new ArrayList<>(Scenario.instances.stream().filter(i -> i.getPlayer() == player).toList());
        return filteredInstances.isEmpty() ? null : filteredInstances.get(0);
    }

    public static ArrayList<Scenario> getInstances()
    {
        return Scenario.instances;
    }

    private final ScenarioEngine engine;
    private final Player player;
    private final ArrayList<ScenarioTask> tasks = new ArrayList<>();

    private int progress = 0;

    public Scenario(ScenarioEngine engine, Player player)
    {
        this.engine = engine;
        this.player = player;

        for (Map.Entry<String, Class<? extends ScenarioTask>> entry : this.engine.getTasks().entrySet())
        {
            try
            {
                this.tasks.add(entry.getValue().getConstructor(String.class, Scenario.class).newInstance(entry.getKey(), this));
            }
            catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
            {
                throw new RuntimeException(e);
            }
        }

        if (! this.tasks.isEmpty())
        {
            Scenario.instances.add(this);
        }
    }

    public ScenarioEngine getEngine()
    {
        return this.engine;
    }

    public Player getPlayer()
    {
        return this.player;
    }

    public ArrayList<ScenarioTask> getTasks()
    {
        return this.tasks;
    }

    public void onPlayerInteract(PlayerInteractEvent event)
    {
        if (this.progress - 1 < this.tasks.size())
        {
            this.tasks.get(this.progress - 1).onPlayerInteract(event);
        }
    }

    public boolean next()
    {
        if (! this.hasNext())
        {
            Scenario.instances.remove(this);
            return false;
        }

        this.tasks.get(this.progress).run(this.player);

        this.progress ++;

        return this.hasNext();
    }

    public boolean hasNext()
    {
        return this.progress < this.tasks.size();
    }
}
