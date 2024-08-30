/*

 © 2024 Azisaba All Rights Reserved.

*/

package net.azisaba.rc;

import net.azisaba.jg.sdk.JunkGame;
import net.azisaba.rc.command.RcCommand;
import net.azisaba.rc.listener.InventoryListener;
import net.azisaba.rc.listener.PlayerListener;
import net.azisaba.rc.quest.DailyQuests;
import net.azisaba.rc.scheduler.DailyQuestShakeTask;
import net.azisaba.rc.ui.CLI;
import net.azisaba.rc.util.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public final class Reincarnation extends JunkGame
{
    private static Reincarnation plugin;

    public static final SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yy");

    public static String DB_URL;
    public static String DB_USER;
    public static String DB_PASS;

    public static Reincarnation getPlugin()
    {
        return Reincarnation.plugin;
    }

    @Override
    public @NotNull Component getDisplayName()
    {
        return Component.text("Reincarnation").color(NamedTextColor.YELLOW).decorate(TextDecoration.BOLD);
    }

    @Override
    public @NotNull Material getFavicon()
    {
        return Material.GOLD_INGOT;
    }

    @Override
    public ArrayList<Component> getLore()
    {
        return new ArrayList<>(List.of(Component.text("PvE").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)));
    }

    @Override
    public void onEnable()
    {
        Reincarnation.plugin = this;

        this.saveDefaultConfig();
        this.getCommand("rc").setExecutor(new RcCommand());

        Bukkit.getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        Reincarnation.DB_URL = this.getConfig().getString("database.url");
        Reincarnation.DB_USER = this.getConfig().getString("database.user");
        Reincarnation.DB_PASS = this.getConfig().getString("database.pass");

        if (! SqlUtility.test(Reincarnation.DB_URL, Reincarnation.DB_USER, Reincarnation.DB_PASS))
        {
            this.getLogger().warning(CLI.RED + "Database connection test failed. Please check config.yml." + CLI.END);
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        if (! QuestUtility.mount())
        {
            this.getLogger().warning("Failed to mount quest engine.");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        GuildUtility.mount();
        UserUtility.mount();
        ScenarioUtility.mount();

        new DailyQuestShakeTask().runTaskLater(this, DailyQuestShakeTask.getLaterTicks());
        DailyQuests.shuffle();
    }

    @Override
    public void onJunkGameCommand(Player player)
    {

    }
}
