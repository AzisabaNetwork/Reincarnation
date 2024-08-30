package net.azisaba.rc.ui.inventory.quest;

import net.azisaba.rc.quest.QuestEngine;
import net.azisaba.rc.quest.QuestType;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class QuestFreeMenuUI extends QuestMenuUI
{
    public QuestFreeMenuUI(Player player)
    {
        super(player);

        this.setQuests(new ArrayList<>(QuestEngine.getInstances().stream().filter(q -> q.getType().equals(QuestType.FREE)).toList()));

        this.addSeparator();
        this.setFocusedTab(5);
        this.rendering(this.page);
    }
}
