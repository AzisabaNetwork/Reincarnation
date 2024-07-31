package net.azisaba.rc.ui;

import net.azisaba.rc.Reincarnation;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;

public class SidePanel
{
    private static final ArrayList<SidePanel> instances = new ArrayList<>();
    private static final ScoreboardManager manager = Bukkit.getScoreboardManager();

    public static SidePanel getInstance(Player player)
    {
        ArrayList<SidePanel> filteredInstances = new ArrayList<>(SidePanel.instances.stream().filter(i -> i.getPlayers().contains(player)).toList());
        return filteredInstances.isEmpty() ? null : filteredInstances.get(0);
    }

    private final Scoreboard scoreboard;
    private final Objective objective;
    private final ArrayList<Component> rows = new ArrayList<>();
    private final ArrayList<Player> players = new ArrayList<>();
    private BukkitRunnable runnable;
    private int blanks = 0;

    public SidePanel()
    {
        this.scoreboard = SidePanel.manager.getNewScoreboard();
        this.objective = this.scoreboard.registerNewObjective("side panel", "dummy", Component.text("DUELS").color(NamedTextColor.YELLOW).decorate(TextDecoration.BOLD));
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        SidePanel.instances.add(this);
    }

    public Component getTitle()
    {
        return this.objective.displayName();
    }

    public void setTitle(Component title)
    {
        this.objective.displayName(title);
    }

    public ArrayList<Component> getRows()
    {
        return this.rows;
    }

    public void setRow(int index, Component row)
    {
        if (this.rows.size() <= index)
        {
            this.rows.add(row);
        }
        else
        {
            this.rows.set(index, row);
        }

        this.scoreboard.getEntries().forEach(this.scoreboard::resetScores);

        int i = this.rows.size();
        for (Component component : this.rows)
        {
            Score score = this.objective.getScore(LegacyComponentSerializer.legacySection().serialize(component));
            score.setScore(i);
            i --;
        }
    }

    public void addRow(Component row)
    {
        this.setRow(this.rows.size(), row);
    }

    public void addRow()
    {
        this.blanks ++;
        this.addRow(Component.text(CLI.getSpaces(this.blanks)));
    }

    public void addRows(int amount)
    {
        if (amount < 0)
        {
            return;
        }

        for (int i = 0; i < amount; i ++)
        {
            this.addRow();
        }
    }

    public void removeRow(int index)
    {
        this.rows.remove(index);
    }

    public void clearRows()
    {
        this.blanks = 0;
        this.rows.clear();
    }

    public ArrayList<Player> getPlayers()
    {
        return this.players;
    }

    public void addPlayer(Player player)
    {
        SidePanel instance2 = SidePanel.getInstance(player);

        if (instance2 != null)
        {
            instance2.removePlayer(player);
        }

        player.setScoreboard(this.scoreboard);
        this.players.add(player);
    }

    public void removePlayer(Player player)
    {
        if (this.players.contains(player))
        {
            player.setScoreboard(SidePanel.manager.getNewScoreboard());
            this.players.remove(player);
        }

        if (this.players.isEmpty())
        {
            SidePanel.instances.remove(this);
            this.runnable.cancel();
        }
    }

    public BukkitRunnable getRunnable()
    {
        return this.runnable;
    }

    public void setRunnable(BukkitRunnable runnable, long period)
    {
        if (this.runnable != null)
        {
            this.runnable.cancel();
        }

        this.runnable = runnable;
        this.runnable.runTaskTimer(Reincarnation.getPlugin(), 0, period);
    }

    public int size()
    {
        return this.rows.size();
    }
}
