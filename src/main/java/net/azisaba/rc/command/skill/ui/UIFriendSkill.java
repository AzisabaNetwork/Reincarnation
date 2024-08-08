package net.azisaba.rc.command.skill.ui;

import net.azisaba.rc.ui.inventory.menu.FriendUI;

public class UIFriendSkill extends AbstractUIOpenSkill
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
