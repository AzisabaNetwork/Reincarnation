package net.azisaba.rc.ui.inventory.quest;

import net.azisaba.rc.quest.QuestEngine;
import org.bukkit.entity.Player;

public class QuestAllMenuUI extends QuestMenuUI
{
    public QuestAllMenuUI(Player player)
    {
        super(player);

        this.setQuests(QuestEngine.getInstances());

        this.addSeparator();
        this.setFocusedTab(1);
        this.rendering(this.page);
    }
}
