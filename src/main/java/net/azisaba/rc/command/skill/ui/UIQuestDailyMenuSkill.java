package net.azisaba.rc.command.skill.ui;

import net.azisaba.rc.ui.inventory.quest.QuestDailyMenuUI;

public class UIQuestDailyMenuSkill extends UISkill
{
    public UIQuestDailyMenuSkill()
    {
        super(QuestDailyMenuUI.class);
    }

    @Override
    public String getName()
    {
        return "ui:quest-daily";
    }
}
