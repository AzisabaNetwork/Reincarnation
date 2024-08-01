package net.azisaba.rc.ui.inventory;

import net.azisaba.rc.guild.Guild;
import net.azisaba.rc.ui.inventory.menu.GameMenuUI;
import net.azisaba.rc.ui.inventory.menu.GuildUI;
import net.azisaba.rc.ui.inventory.menu.ProfileUI;
import net.azisaba.rc.user.User;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Collections;

public class SocialMenuUI extends GameMenuUI
{

    public SocialMenuUI(Player player, OfflinePlayer view)
    {
        super(player, User.getInstance(view.getUniqueId().toString()).getRankedName());

        if (player.getUniqueId().equals(view.getUniqueId()))
        {
            new ProfileUI(player);
            return;
        }

        this.addSeparator();

        User user = User.getInstance(player);
        User viewUser = User.getInstance(view.getUniqueId().toString());

        ItemStack stack0 = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta0 = (SkullMeta) stack0.getItemMeta();
        meta0.displayName(viewUser.getRankedName());
        meta0.setOwningPlayer(view);
        stack0.setItemMeta(meta0);
        this.inventory.setItem(0, stack0);

        ItemStack stack21 = new ItemStack(Material.STICK);
        ItemMeta meta21 = stack21.getItemMeta();
        meta21.displayName(Component.text("YouTube").color(TextColor.color(255, 0, 0)).decoration(TextDecoration.ITALIC, false));
        meta21.lore(Collections.singletonList(Component.text("@username").color(NamedTextColor.GRAY)));
        stack21.setItemMeta(meta21);
        this.inventory.setItem(21, stack21);

        ItemStack stack22 = new ItemStack(Material.STICK);
        ItemMeta meta22 = stack22.getItemMeta();
        meta22.displayName(Component.text("Twitter").color(TextColor.color(29, 161, 242)).decoration(TextDecoration.ITALIC, false));
        meta22.lore(Collections.singletonList(Component.text("@username").color(NamedTextColor.GRAY)));
        stack22.setItemMeta(meta22);
        this.inventory.setItem(22, stack22);

        ItemStack stack23 = new ItemStack(Material.STICK);
        ItemMeta meta23 = stack23.getItemMeta();
        meta23.displayName(Component.text("Discord").color(TextColor.color(88, 101, 242)).decoration(TextDecoration.ITALIC, false));
        meta23.lore(Collections.singletonList(Component.text("username").color(NamedTextColor.GRAY)));
        stack23.setItemMeta(meta23);
        this.inventory.setItem(23, stack23);

        ItemStack stack30 = new ItemStack(Material.PAPER);
        ItemMeta meta30 = stack30.getItemMeta();
        meta30.displayName(Component.text("報告").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false));
        meta30.lore(Collections.singletonList(Component.text("このプレイヤーを報告します").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)));
        stack30.setItemMeta(meta30);
        this.inventory.setItem(30, stack30);

        ItemStack stack31 = new ItemStack(user.isFriend(viewUser) ? Material.REDSTONE_BLOCK : Material.DIAMOND);
        ItemMeta meta31 = stack31.getItemMeta();
        meta31.displayName(Component.text(user.isFriend(viewUser) ? "フレンドを削除" : "フレンドを申請").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false));
        stack31.setItemMeta(meta31);
        this.register(31, stack31, user.isFriend(viewUser) ? String.format("rc social:unfriend %s %s", player.getName(), view.getName()) : String.format("rc social:friend-request %s %s", player.getName(), view.getName()));

        if (user.getGuild() != null)
        {
            Guild guild = user.getGuild();
            ItemStack stack32 = new ItemStack(guild.isMember(viewUser) ? Material.BARRIER : Material.BOW);
            ItemMeta meta32 = stack32.getItemMeta();
            meta32.displayName(Component.text(guild.isMember(viewUser) ? "Guild から追放" : "Guild に招待").color(guild.isMember(viewUser) ? NamedTextColor.RED : NamedTextColor.GREEN));
            stack32.setItemMeta(meta32);
            this.register(32, stack32, guild.isMember(viewUser) ? String.format("rc guild:kick %s", view.getName()) : String.format("rc guild:invite %s %s", guild.getId(), view.getName()));
        }
    }
}
