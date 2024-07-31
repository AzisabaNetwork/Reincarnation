package net.azisaba.rc.ui.inventory;

import net.azisaba.rc.quest.Party;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Collections;

public class PartyInviteUI extends InventoryUI
{
    private int page = 0;
    private final Party party;

    public PartyInviteUI(Player player, Party party)
    {
        super(player, Bukkit.createInventory(null, 54, Component.text("招待を作成…")));

        this.party = party;

        ItemStack backStack = new ItemStack(Material.ARROW);
        ItemMeta backMeta = backStack.getItemMeta();
        backMeta.displayName(Component.text("戻る…").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
        backStack.setItemMeta(backMeta);
        this.inventory.setItem(45, backStack);

        ItemStack nextStack = new ItemStack(Material.ARROW);
        ItemMeta nextMeta = nextStack.getItemMeta();
        nextMeta.displayName(Component.text("次へ…").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
        nextStack.setItemMeta(nextMeta);
        this.inventory.setItem(53, nextStack);

        ItemStack closeStack = new ItemStack(Material.BARRIER);
        ItemMeta closeMeta = closeStack.getItemMeta();
        closeMeta.displayName(Component.text("前のページに戻る").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false));
        closeStack.setItemMeta(closeMeta);
        this.register(49, closeStack, "rc ui:party " + player.getName());

        this.rendering(this.page);
    }

    public void rendering(int page)
    {
        ArrayList<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());

        int index = 0;
        for (int i = page * 36; i < (page + 1) * 36; i ++)
        {
            if (players.size() <= i)
            {
                break;
            }

            Player player = players.get(i);

            ItemStack playerStack = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta playerMeta = (SkullMeta) playerStack.getItemMeta();
            playerMeta.displayName(player.displayName().decoration(TextDecoration.ITALIC, false));

            if (this.party.isMember(player))
            {
                playerMeta.lore(Collections.singletonList(Component.text("既に Party に参加しています！").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false)));
            }

            playerMeta.setOwningPlayer(player);
            playerStack.setItemMeta(playerMeta);

            this.register(index, playerStack, "rc party:invite " + this.party.getId() + " " + player.getName());

            index ++;
        }
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event)
    {
        super.onInventoryClick(event);

        if (event.getSlot() == 45)
        {
            this.page -= (0 < this.page) ? 1 : 0;
            this.rendering(this.page);
        }

        if (event.getSlot() == 53 && this.inventory.getItem(35) != null  && this.page < (Bukkit.getOnlinePlayers().size() / 36))
        {
            this.page ++;
            this.rendering(this.page);
        }
    }
}
