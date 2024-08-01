package net.azisaba.rc.ui.inventory.menu;

import net.azisaba.rc.guild.Guild;
import net.azisaba.rc.ui.inventory.PlayerSelectorUI;
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

public class GuildMemberUI extends PlayerSelectorUI
{
    private final Guild guild;

    public GuildMemberUI(Player player, Guild guild)
    {
        super(player, Component.text("ギルドメンバー"));
        this.guild = guild;

        if (User.getInstance(player) == this.guild.getMaster())
        {
            ItemStack inviteStack = new ItemStack(Material.COMPASS);
            ItemMeta inviteMeta = inviteStack.getItemMeta();
            inviteMeta.displayName(Component.text("探索").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false));
            inviteMeta.lore(Collections.singletonList(Component.text("Guild に新しいプレイヤーを招待する").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false)));
            inviteStack.setItemMeta(inviteMeta);
            this.register(49, inviteStack, String.format("rc ui:guild-invite %s %s", player.getName(), this.guild.getId()));
        }

        this.draw(this.p);
    }

    @Override
    public ArrayList<OfflinePlayer> getPlayers()
    {
        ArrayList<OfflinePlayer> members = new ArrayList<>();

        this.guild.getMembers().stream().filter(User::isOnline).toList().forEach(m -> members.add(m.getAsOfflinePlayer()));
        this.guild.getMembers().stream().filter(m -> ! m.isOnline()).toList().forEach(m -> members.add(m.getAsOfflinePlayer()));

        return members;
    }

    @Override
    public String getCommand(OfflinePlayer player)
    {
        return String.format("rc ui:social-menu %s %s", this.player.getName(), player.getName());
    }

    @Override
    public ItemStack getPlayerStack(OfflinePlayer player)
    {
        ItemStack playerStack = super.getPlayerStack(player);
        ItemMeta playerMeta = playerStack.getItemMeta();

        if (Bukkit.getPlayerExact(player.getName()) != null)
        {
            playerMeta.lore(Collections.singletonList(Component.text("オンライン").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false)));
        }
        else
        {
            playerMeta.lore(Collections.singletonList(Component.text("オフライン").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false)));
        }

        playerStack.setItemMeta(playerMeta);
        return playerStack;
    }
}
