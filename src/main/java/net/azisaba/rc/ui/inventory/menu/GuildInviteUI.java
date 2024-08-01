package net.azisaba.rc.ui.inventory.menu;

import net.azisaba.rc.guild.Guild;
import net.azisaba.rc.ui.inventory.PlayerSelectorUI;
import net.azisaba.rc.user.User;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GuildInviteUI extends PlayerSelectorUI
{
    private final Guild guild;

    public GuildInviteUI(Player player, Guild guild)
    {
        super(player, Component.text("ギルドメンバー"));
        this.guild = guild;

        ItemStack closeStack = new ItemStack(Material.BARRIER);
        ItemMeta closeMeta = closeStack.getItemMeta();
        closeMeta.displayName(Component.text("Guild ページへ").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false));
        closeStack.setItemMeta(closeMeta);
        this.register(49, closeStack, String.format("rc ui:guild %s", player.getName()));

        this.draw(this.p);
    }

    @Override
    public String getCommand(OfflinePlayer player)
    {
        return String.format("rc guild:invite %s %s", this.guild.getId(), player.getName());
    }

    @Override
    public ArrayList<OfflinePlayer> getPlayers()
    {
        ArrayList<OfflinePlayer> players = new ArrayList<>();
        User.getInstances().stream().filter(i -> ! this.guild.isMember(i) && i.isOnline()).toList().forEach(u -> players.add(u.getAsOfflinePlayer()));
        return players;
    }
}
