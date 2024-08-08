package net.azisaba.rc.command.skill.ui;

import net.azisaba.rc.ui.inventory.menu.FriendRequestUI;

public class UIFriendRequestSkill extends AbstractUIOpenSkill
{

    public UIFriendRequestSkill()
    {
        super(FriendRequestUI.class);
    }

    @Override
    public String getName()
    {
        return "ui:friend-request";
    }
}
