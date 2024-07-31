package net.azisaba.rc.ui.inventory.quest;

import net.azisaba.rc.quest.DailyQuests;
import org.bukkit.entity.Player;

public class QuestDailyMenuUI extends QuestMenuUI
{

    public QuestDailyMenuUI(Player player)
    {
        super(player);

        this.setQuests(DailyQuests.quests);

        this.addSeparator();
        this.setFocusedTab(3);
        this.rendering(this.page);
    }
}
