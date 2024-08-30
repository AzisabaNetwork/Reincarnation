package net.azisaba.rc.command.skill.ui;

import net.azisaba.rc.ui.inventory.quest.QuestFreeMenuUI;

public class UIQuestFreeMenuSkill extends UISkill
{
    public UIQuestFreeMenuSkill()
    {
        super(QuestFreeMenuUI.class);
    }

    @Override
    public String getName()
    {
        return "ui:quest-free";
    }
}
