package net.azisaba.rc.ui.inventory;

import net.azisaba.rc.quest.Party;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class PartyInviteUI extends PlayerSelectorUI
{
    private final Party party;

    public PartyInviteUI(Player player, Party party)
    {
        super(player, Component.text("招待を作成…"));

        this.party = party;

        ItemStack closeStack = new ItemStack(Material.BARRIER);
        ItemMeta closeMeta = closeStack.getItemMeta();
        closeMeta.displayName(Component.text("前のページに戻る").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false));
        closeStack.setItemMeta(closeMeta);
        this.addListener(49, closeStack, String.format("rc ui:party %s", player.getName()));

        this.draw(this.p);
    }

    @Override
    public String getCommand(OfflinePlayer player)
    {
        return String.format("rc party:invite %s %s", this.party.getId(), player.getName());
    }

    @Override
    public ItemStack getPlayerStack(OfflinePlayer offlinePlayer)
    {
        ItemStack playerStack = super.getPlayerStack(offlinePlayer);

        if (Bukkit.getPlayerExact(offlinePlayer.getName()) == null)
        {
            return playerStack;
        }

        Player player = Bukkit.getPlayerExact(offlinePlayer.getName());

        if (this.party.isMember(player))
        {
            ItemMeta playerMeta = playerStack.getItemMeta();
            playerMeta.lore(Collections.singletonList(Component.text("既に Party に参加しています！").color(NamedTextColor.RED)));
            playerStack.setItemMeta(playerMeta);
        }

        return playerStack;
    }
}
