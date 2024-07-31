package net.azisaba.rc.ui.inventory.quest;

import net.azisaba.rc.quest.QuestEngine;
import net.azisaba.rc.quest.QuestType;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class QuestStoryMenuUI extends QuestMenuUI
{

    public QuestStoryMenuUI(Player player)
    {
        super(player);

        this.setQuests(new ArrayList<>(QuestEngine.getInstances().stream().filter(q -> q.getType().equals(QuestType.STORY)).toList()));

        this.addSeparator();
        this.setFocusedTab(2);
        this.rendering(this.page);
    }
}
