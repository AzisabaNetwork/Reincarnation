package net.azisaba.rc.quest;

import net.azisaba.rc.util.ResourceUtility;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;

public class QuestEngine
{
    private static final ArrayList<QuestEngine> instances = new ArrayList<>();

    public static QuestEngine getInstance(String id)
    {
        ArrayList<QuestEngine> filteredInstances = new ArrayList<>(QuestEngine.instances.stream().filter(i -> i.getId().equals(id)).toList());
        return filteredInstances.isEmpty() ? null : filteredInstances.get(0);
    }

    public static ArrayList<QuestEngine> getInstances()
    {
        return QuestEngine.instances;
    }

    private final String id;

    private final String display;
    private final ArrayList<String> lore;
    private final Material favicon;

    private final QuestType type;
    private final int count;
    private final int maxMember;
    private final int amount;

    private final Location spawn;
    private final World world;
    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;

    private final ArrayList<String> startScripts;
    private final ArrayList<String> endScripts;

    private final YamlConfiguration parameters;

    public QuestEngine(String id)
    {
        this.id = id;

        this.parameters = ResourceUtility.getYamlResource(String.format("/quests/%s.yml", this.id));

        this.display = this.parameters.getString("Display");
        this.lore = new ArrayList<>(this.parameters.getStringList("Lore"));
        this.favicon = Material.valueOf(this.parameters.getString("Favicon").toUpperCase());

        this.type = QuestType.valueOf(this.parameters.getString("Type").toUpperCase());
        this.count = this.parameters.getInt("Count");
        this.maxMember = this.parameters.getInt("MaxMember");
        this.amount = this.parameters.getInt("Amount");

        this.world = Bukkit.getWorld(this.parameters.getString("Spawn.World"));
        this.x = this.parameters.getDouble("Spawn.X");
        this.y = this.parameters.getDouble("Spawn.Y");
        this.z = this.parameters.getDouble("Spawn.Z");
        this.yaw = this.parameters.contains("Spawn.Yaw") ? (float) this.parameters.getDouble("Spawn.Yaw") : 0.0f;
        this.pitch = this.parameters.contains("Spawn.Pitch") ? (float) this.parameters.getDouble("Spawn.Pitch") : 0.0f;

        this.spawn = new Location(this.world, this.x, this.y, this.z, this.yaw, this.pitch);

        this.startScripts = new ArrayList<>(this.parameters.getStringList("Start"));
        this.endScripts = new ArrayList<>(this.parameters.getStringList("End"));

        QuestEngine.instances.add(this);
    }

    public String getId()
    {
        return this.id;
    }

    public String getDisplay()
    {
        return this.display;
    }

    public ArrayList<String> getLore()
    {
        return this.lore;
    }

    public Material getFavicon()
    {
        return this.favicon;
    }

    public QuestType getType()
    {
        return this.type;
    }

    public int getCount()
    {
        return this.count;
    }

    public int getMaxMember()
    {
        return this.maxMember;
    }

    public int getAmount()
    {
        return this.amount;
    }

    public Location getSpawn()
    {
        return this.spawn;
    }

    public void runStartScripts()
    {
        for (String script : this.startScripts)
        {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), script);
        }
    }

    public void runEndScripts()
    {
        for (String script : this.endScripts)
        {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), script);
        }
    }
}
