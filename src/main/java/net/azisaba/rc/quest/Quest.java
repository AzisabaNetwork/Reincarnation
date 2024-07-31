package net.azisaba.rc.quest;

import net.azisaba.rc.Reincarnation;
import net.azisaba.rc.ui.AnimationBuilder;
import net.azisaba.rc.ui.CLI;
import net.azisaba.rc.ui.SidePanel;
import net.azisaba.rc.util.QuestUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Date;

public class Quest
{
    private static final ArrayList<Quest> instances = new ArrayList<>();

    public static Quest getInstance(String id)
    {
        ArrayList<Quest> filteredInstances = new ArrayList<>(Quest.instances.stream().filter(i -> i.getId().equals(id)).toList());
        return filteredInstances.isEmpty() ? null : filteredInstances.get(0);
    }

    public static ArrayList<Quest> getInstances()
    {
        return Quest.instances;
    }

    private final String id;

    private final QuestEngine engine;
    private final Party party;
    private final SidePanel panel;
    private int progress = 0;

    public Quest(QuestEngine engine, Party party)
    {
        this.id = QuestUtil.getId();
        this.engine = engine;
        this.party = party;
        this.panel = new SidePanel();

        this.party.setQuest(this);

        this.panel.setTitle(Component.text("  REINCARNATION  "));
        this.panel.addRows(7 + this.party.getMembers().size());
        this.panel.addRow(Component.text("www.azisaba.net").color(NamedTextColor.YELLOW));

        this.panel.setRunnable(new BukkitRunnable()
        {
            private final Component[] title = AnimationBuilder.HYPIXEL("  REINCARNATION  ");
            private int tick = 0;

            @Override
            public void run()
            {
                panel.setTitle(this.title[this.tick]);
                panel.setRow(0, Component.text(Reincarnation.sdf1.format(new Date())).color(NamedTextColor.GRAY));
                panel.setRow(2, Component.text("進行中: ").append(Component.text(engine.getDisplay()).color(NamedTextColor.GREEN)));
                panel.setRow(3, Component.text("進捗: ").append(Component.text(progress + "/" + engine.getAmount()).color(NamedTextColor.GREEN)));
                panel.setRow(5, Component.text("Party (" + party.getMembers().size() + "):"));

                for (int i = 0; i < panel.size() - 8; i ++)
                {
                    if (party.getMembers().size() <= i)
                    {
                        panel.setRow(i + 6, Component.text("切断").color(NamedTextColor.RED));
                    }

                    Player member = party.getMembers().get(i);
                    panel.setRow(i + 6, Component.text("-" + member.getName() + " ").color((member == party.getLeader()) ? NamedTextColor.AQUA : NamedTextColor.GRAY)
                            .append(Component.text((int) member.getHealth()).color(NamedTextColor.GREEN))
                            .append(Component.text("♥").color(NamedTextColor.RED)));
                }

                this.tick = (this.title.length <= this.tick + 1) ? 0 : this.tick + 1;
            }
        }, 10L);

        this.party.getMembers().forEach(this.panel::addPlayer);

        this.start();
        Quest.instances.add(this);
    }

    public String getId()
    {
        return this.id;
    }

    public QuestEngine getEngine()
    {
        return this.engine;
    }

    public Party getParty()
    {
        return this.party;
    }

    public int getProgress()
    {
        return this.progress;
    }

    public void setProgress(int progress)
    {
        this.progress = progress;

        if (this.engine.getAmount() <= this.progress)
        {
            this.end();
        }
    }

    public void start()
    {
        this.party.getMembers().forEach(m -> m.teleport(this.engine.getSpawn()));

        this.party.broadcast(Component.text(CLI.SEPARATOR).color(NamedTextColor.GREEN));
        this.party.broadcast(Component.text(""));
        this.party.broadcast(Component.text(CLI.getSpaces(28 - this.engine.getDisplay().length() / 2) + this.engine.getDisplay()).decorate(TextDecoration.BOLD));
        for (String row : this.engine.getLore())
        {
            this.party.getMembers().forEach(m -> m.sendMessage(CLI.getSpaces(28 - row.length() / 2) + row));
        }
        this.party.broadcast(Component.text(""));
        this.party.broadcast(Component.text(CLI.SEPARATOR).color(NamedTextColor.GREEN));

        this.engine.runStartScripts();
    }

    public void end()
    {
        this.party.getMembers().forEach(this.panel::removePlayer);
        this.party.setQuest(null);

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                engine.runEndScripts();
            }
        }.runTaskLater(Reincarnation.getPlugin(), 20L * 5);
    }
}
