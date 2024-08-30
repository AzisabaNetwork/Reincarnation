package net.azisaba.rc.command.skill.ui;

import net.azisaba.rc.ui.inventory.gamemenu.SettingsUI;

public class UISettingsSkill extends UISkill
{
    public UISettingsSkill()
    {
        super(SettingsUI.class);
    }

    @Override
    public String getName()
    {
        return "ui:settings";
    }
}
