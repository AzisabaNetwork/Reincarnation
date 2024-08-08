package net.azisaba.rc.command.skill.ui;

import net.azisaba.rc.ui.inventory.quest.QuestEventMenuUI;

public class UIQuestEventMenuSkill extends AbstractUIOpenSkill
{

    public UIQuestEventMenuSkill()
    {
        super(QuestEventMenuUI.class);
    }

    @Override
    public String getName()
    {
        return "ui:quest-event";
    }
}
