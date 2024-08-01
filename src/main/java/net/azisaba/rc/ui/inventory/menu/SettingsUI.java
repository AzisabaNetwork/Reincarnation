package net.azisaba.rc.ui.inventory.menu;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class SettingsUI extends GameMenuUI
{

    public SettingsUI(Player player)
    {
        super(player, Component.text("設定"));

        this.addSeparator();
        this.setFocusedTab(5);
    }
}
