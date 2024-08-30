package net.azisaba.rc.command.skill.ui;

import net.azisaba.rc.ui.inventory.gamemenu.FriendRequestUI;

public class UIFriendRequestSkill extends UISkill
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
