package net.azisaba.rc.command.skill.ui;

import net.azisaba.rc.ui.inventory.gamemenu.FriendUI;

public class UIFriendSkill extends UISkill
{
    public UIFriendSkill()
    {
        super(FriendUI.class);
    }

    @Override
    public String getName()
    {
        return "ui:friend";
    }
}
