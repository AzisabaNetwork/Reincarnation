package net.azisaba.rc.scenario;

import net.azisaba.rc.scenario.task.*;
import net.azisaba.rc.util.ResourceUtility;
import org.bukkit.configuration.file.YamlConfiguration;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScenarioEngine
{
    private static final ArrayList<ScenarioEngine> instances = new ArrayList<>();
    private static final HashMap<Pattern, Class<? extends ScenarioTask>> syntax = new HashMap<>();

    static
    {
        ScenarioEngine.registerSyntax(CommandTask.class);
        ScenarioEngine.registerSyntax(DelayTask.class);
        ScenarioEngine.registerSyntax(HologramTask.class);
        ScenarioEngine.registerSyntax(SelectTask.class);
    }

    public static ScenarioEngine getInstance(String name)
    {
        ArrayList<ScenarioEngine> filteredInstances = new ArrayList<>(ScenarioEngine.instances.stream().filter(i -> i.getName().equals(name)).toList());
        return filteredInstances.isEmpty() ? null : filteredInstances.get(0);
    }

    public static ArrayList<ScenarioEngine> getInstances()
    {
        return ScenarioEngine.instances;
    }

    public static void registerSyntax(Class<? extends ScenarioTask> clazz)
    {
        try
        {
            Pattern pattern = (Pattern) clazz.getMethod("getPattern").invoke(null);
            ScenarioEngine.syntax.put(pattern, clazz);
        }
        catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e)
        {
            throw new RuntimeException(e);
        }
    }

    private final String name;

    private final LinkedHashMap<String, Class<? extends ScenarioTask>> tasks = new LinkedHashMap<>();
    private final YamlConfiguration config;

    public ScenarioEngine(String name)
    {
        this.name = name;
        this.config = ResourceUtility.getYamlResource(String.format("/scenarios/%s", name));

        for (String key : this.config.getKeys(false))
        {
            Class<? extends ScenarioTask> clazz = null;

            for (Map.Entry<Pattern, Class<? extends ScenarioTask>> entry : ScenarioEngine.syntax.entrySet())
            {
                Matcher matcher = entry.getKey().matcher(key);
                clazz = matcher.matches() ? entry.getValue() : clazz;
            }

            if (clazz != null)
            {
                this.tasks.put(key, clazz);
            }
        }

        ScenarioEngine.instances.add(this);
    }

    public String getName()
    {
        return this.name;
    }

    public YamlConfiguration getConfig()
    {
        return this.config;
    }

    public HashMap<String, Class<? extends ScenarioTask>> getTasks()
    {
        return this.tasks;
    }
}
