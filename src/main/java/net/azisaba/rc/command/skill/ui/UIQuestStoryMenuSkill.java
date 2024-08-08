package net.azisaba.rc.command.skill.ui;

import net.azisaba.rc.ui.inventory.quest.QuestStoryMenuUI;

public class UIQuestStoryMenuSkill extends AbstractUIOpenSkill
{

    public UIQuestStoryMenuSkill()
    {
        super(QuestStoryMenuUI.class);
    }

    @Override
    public String getName()
    {
        return "quest-story";
    }
}
