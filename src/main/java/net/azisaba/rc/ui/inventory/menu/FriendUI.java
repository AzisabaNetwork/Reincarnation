package net.azisaba.rc.ui.inventory.menu;

import net.azisaba.rc.user.User;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

public class FriendUI extends AbstractPlayerMenuUI
{

    public FriendUI(Player player)
    {
        super(player, Component.text("フレンド"));

        ItemStack requestStack = new ItemStack(Material.COMPASS);
        ItemMeta requestMeta = requestStack.getItemMeta();
        requestMeta.displayName(Component.text("探索").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false));
        requestMeta.lore(Collections.singletonList(Component.text("新しい Friend を探してみましょう").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)));
        requestStack.setItemMeta(requestMeta);
        this.register(49, requestStack, String.format("rc ui:friend-request %s", player.getName()));

        this.addSeparator();
        this.setFocusedTab(2);
        this.j1 = 18;
        this.j2 = 36;

        this.draw(this.p);
    }

    @Override
    public String getCommand(OfflinePlayer player)
    {
        return String.format("rc ui:social-menu %s %s", this.player.getName(), player.getName());
    }

    @Override
    public ArrayList<OfflinePlayer> getPlayers()
    {
        final ArrayList<OfflinePlayer> friends = new ArrayList<>();
        final User user = User.getInstance(this.player);
        user.getFriends().forEach(f -> friends.add(Bukkit.getOfflinePlayer(UUID.fromString(f.getId()))));
        return friends;
    }
}
