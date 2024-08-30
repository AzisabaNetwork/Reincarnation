package net.azisaba.rc.command.skill.ui;

import net.azisaba.rc.ui.inventory.quest.QuestStoryMenuUI;

public class UIQuestStoryMenuSkill extends UISkill
{
    public UIQuestStoryMenuSkill()
    {
        super(QuestStoryMenuUI.class);
    }

    @Override
    public String getName()
    {
        return "ui:quest-story";
    }
}
