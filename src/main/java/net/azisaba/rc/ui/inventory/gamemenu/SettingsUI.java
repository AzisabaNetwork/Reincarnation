package net.azisaba.rc.ui.inventory.gamemenu;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class SettingsUI extends GameMenuUI
{
    public SettingsUI(Player player)
    {
        super(player, Component.text("Settings"));

        this.addSeparator();
        this.setFocusedTab(5);
    }
}
