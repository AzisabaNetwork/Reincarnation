package net.azisaba.rc.command.skill.ui;

import net.azisaba.rc.ui.inventory.menu.PartyUI;

public class UIPartySkill extends AbstractUIOpenSkill
{

    public UIPartySkill()
    {
        super(PartyUI.class);
    }

    @Override
    public String getName()
    {
        return "ui:party";
    }
}
