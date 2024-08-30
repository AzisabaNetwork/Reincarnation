package net.azisaba.rc.command.skill.ui;

import net.azisaba.rc.ui.inventory.gamemenu.PartyUI;

public class UIPartySkill extends UISkill
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
