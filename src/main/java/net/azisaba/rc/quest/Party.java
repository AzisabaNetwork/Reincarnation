package net.azisaba.rc.quest;

import net.azisaba.rc.util.PartyUtil;
import net.azisaba.rc.util.UserUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Party
{
    private static final ArrayList<Party> instances = new ArrayList<>();

    public static Party getInstance(String id)
    {
        ArrayList<Party> filteredInstances = new ArrayList<>(Party.instances.stream().filter(p -> p.getId().equals(id)).toList());
        return filteredInstances.isEmpty() ? null : filteredInstances.get(0);
    }

    public static ArrayList<Party> getInstances()
    {
        return Party.instances;
    }

    private final String id;

    private Player leader;
    private final ArrayList<Player> members = new ArrayList<>();

    private Quest quest = null;

    public Party(Player leader)
    {
        this.id = PartyUtil.getId();
        this.leader = leader;
        this.members.add(this.leader);

        this.leader.sendMessage(Component.text("Hosted a new party…").color(NamedTextColor.GREEN));

        Party.instances.add(this);
    }

    public String getId()
    {
        return this.id;
    }

    public Player getLeader()
    {
        return this.leader;
    }

    public void setLeader(Player leader)
    {
        if (! this.isMember(leader))
        {
            return;
        }

        this.leader = leader;
    }

    public ArrayList<Player> getMembers()
    {
        return this.members;
    }

    public boolean isMember(Player player)
    {
        return this.members.contains(player);
    }

    public Quest getQuest()
    {
        return this.quest;
    }

    public void setQuest(Quest quest)
    {
        this.quest = quest;
    }

    public void broadcast(Component msg)
    {
        this.members.forEach(m -> m.sendMessage(msg));
    }

    public void join(Player player)
    {
        if (this.isMember(player))
        {
            return;
        }

        this.members.add(player);

        Component message = player.displayName()
                .append(Component.text(" が Party に参加しました！").color(NamedTextColor.YELLOW));
        this.broadcast(message);
    }

    public void quit(Player player)
    {
        this.members.remove(player);

        Component message = player.displayName()
                .append(Component.text(" が Party を退出しました").color(NamedTextColor.YELLOW));
        this.broadcast(message);

        player.sendMessage(Component.text("Party を退出しました").color(NamedTextColor.RED));

        if (this.leader == player)
        {
            this.close();
        }

        if (this.quest != null)
        {
            UserUtil.sidePanel(player);
        }
    }

    public void close()
    {
        Party.instances.remove(this);

        if (this.quest != null)
        {
            this.quest.end();
        }

        Component message = Component.text("Party は解散されました…").color(NamedTextColor.RED);
        this.broadcast(message);
        this.members.clear();
    }
}
