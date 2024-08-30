package net.azisaba.rc.command.skill.ui;

import net.azisaba.rc.ui.inventory.gamemenu.GuildUI;

public class UIGuildSkill extends UISkill
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
