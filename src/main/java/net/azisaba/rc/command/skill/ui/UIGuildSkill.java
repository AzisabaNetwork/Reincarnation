package net.azisaba.rc.command.skill.ui;

import net.azisaba.rc.ui.inventory.menu.GuildUI;

public class UIGuildSkill extends AbstractUIOpenSkill
{

    public UIGuildSkill()
    {
        super(GuildUI.class);
    }

    @Override
    public String getName()
    {
        return "ui:guild";
    }
}
