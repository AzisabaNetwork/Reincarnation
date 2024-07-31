/*

 © 2024 Azisaba All Rights Reserved.

*/

package net.azisaba.rc;

import net.azisaba.rc.command.RcCommand;
import net.azisaba.rc.listener.InventoryListener;
import net.azisaba.rc.listener.PlayerListener;
import net.azisaba.rc.quest.DailyQuests;
import net.azisaba.rc.scheduler.DailyQuestShakeTask;
import net.azisaba.rc.util.GuildUtil;
import net.azisaba.rc.util.QuestUtil;
import net.azisaba.rc.util.SQLUtil;
import net.azisaba.rc.util.UserUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;

public final class Reincarnation extends JavaPlugin
{
    private static Reincarnation plugin;
    private static BukkitRunnable runnable;

    public static final SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yy");

    public static String DB_URL;
    public static String DB_USER;
    public static String DB_PASS;

    public static Reincarnation getPlugin()
    {
        return Reincarnation.plugin;
    }

    public void tick()
    {

    }

    @Override
    public void onEnable()
    {
        Reincarnation.plugin = this;
        Reincarnation.runnable = new BukkitRunnable()
        {
            @Override
            public void run()
            {
                tick();
            }
        };
        Reincarnation.runnable.runTaskTimer(this, 0L, 20L);

        this.saveDefaultConfig();
        this.getCommand("rc").setExecutor(new RcCommand());

        Reincarnation.DB_URL = this.getConfig().getString("database.url");
        Reincarnation.DB_USER = this.getConfig().getString("database.user");
        Reincarnation.DB_PASS = this.getConfig().getString("database.pass");

        if (! SQLUtil.test(Reincarnation.DB_URL, Reincarnation.DB_USER, Reincarnation.DB_PASS))
        {
            this.getLogger().warning("Database connection test failed. Please check config.yml.");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        Bukkit.getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        if (! QuestUtil.mount())
        {
            this.getLogger().warning("Failed to mount quest engine.");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        GuildUtil.mount();
        UserUtil.mount();

        new DailyQuestShakeTask().runTaskLater(this, DailyQuestShakeTask.getLaterTicks());
        DailyQuests.shuffle();
    }
}
