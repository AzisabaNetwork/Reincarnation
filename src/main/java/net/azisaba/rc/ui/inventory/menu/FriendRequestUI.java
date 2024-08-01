package net.azisaba.rc.ui.inventory.menu;

import net.azisaba.rc.ui.inventory.PlayerSelectorUI;
import net.azisaba.rc.user.User;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;

public class FriendRequestUI extends PlayerSelectorUI
{

    public FriendRequestUI(Player player)
    {
        super(player, Component.text("探索…"));
        this.draw(this.p);
    }

    @Override
    public String getCommand(OfflinePlayer player)
    {
        return String.format("rc social:friend-request %s %s", this.player.getName(), player.getName());
    }

    @Override
    public ItemStack getPlayerStack(OfflinePlayer player)
    {
        ItemStack playerStack = super.getPlayerStack(player);
        ItemMeta playerMeta = playerStack.getItemMeta();
        playerMeta.lore(Collections.singletonList(Component.text("クリックして Friend を申請").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false)));
        playerStack.setItemMeta(playerMeta);
        return playerStack;
    }

    @Override
    public ArrayList<OfflinePlayer> getPlayers()
    {
        User user = User.getInstance(this.player);
        return new ArrayList<>(super.getPlayers().stream().filter(p -> (! user.isFriend(User.getInstance(p.getUniqueId().toString()))) && user != User.getInstance(p.getUniqueId().toString())).toList());
    }
}
