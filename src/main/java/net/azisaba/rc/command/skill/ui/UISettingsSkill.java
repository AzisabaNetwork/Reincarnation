package net.azisaba.rc.command.skill.ui;

import net.azisaba.rc.ui.inventory.menu.SettingsUI;

public class UISettingsSkill extends AbstractUIOpenSkill
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
