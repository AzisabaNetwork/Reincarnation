package net.azisaba.rc.command.skill.ui;

import net.azisaba.rc.ui.inventory.quest.QuestAllMenuUI;

public class UIQuestAllMenuSkill extends AbstractUIOpenSkill
{

    public UIQuestAllMenuSkill()
    {
        super(QuestAllMenuUI.class);
    }

    @Override
    public String getName()
    {
        return "ui:quest-all";
    }
}
