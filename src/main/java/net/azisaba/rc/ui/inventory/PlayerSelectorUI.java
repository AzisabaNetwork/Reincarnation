package net.azisaba.rc.ui.inventory;

import net.azisaba.rc.user.User;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

// Note: We are working on merging with AbstractPlayerMenuUI.

@Deprecated
public abstract class PlayerSelectorUI extends AbstractInventoryUI
{
    protected int j1 = 0;
    protected int j2 = 36;
    protected int p = 0;

    public PlayerSelectorUI(Player player, Component title)
    {
        super(player, Bukkit.createInventory(null, 54, title));

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
    }

    public String getCommand(OfflinePlayer player)
    {
        return null;
    }

    public ItemStack getPlayerStack(OfflinePlayer player)
    {
        User user = User.getInstance(player.getUniqueId().toString());

        ItemStack playerStack = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta playerMeta = (SkullMeta) playerStack.getItemMeta();
        playerMeta.displayName(user.getRankedName().decoration(TextDecoration.ITALIC, false));
        playerMeta.setOwningPlayer(player);
        playerStack.setItemMeta(playerMeta);
        return playerStack;
    }

    public ArrayList<OfflinePlayer> getPlayers()
    {
        return new ArrayList<>(Bukkit.getOnlinePlayers());
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event)
    {
        super.onInventoryClick(event);

        if (event.getSlot() == 45)
        {
            this.p -= (0 < this.p) ? 1 : 0;
            this.draw(this.p);
        }

        if (event.getSlot() == 53 && this.inventory.getItem(35) != null  && this.p < (Bukkit.getOnlinePlayers().size() / 36))
        {
            this.p ++;
            this.draw(this.p);
        }
    }

    public void draw(int p)
    {
        ArrayList<OfflinePlayer> players = this.getPlayers();
        int pgsize = this.j2 - this.j1;
        int index = this.j1;

        for (int i = p * pgsize; i < (p + 1) * pgsize; i ++)
        {
            if (players.size() <= i)
            {
                break;
            }

            OfflinePlayer player = players.get(i);
            String command = this.getCommand(player);

            if (command == null)
            {
                this.inventory.setItem(index, this.getPlayerStack(player));
            }
            else
            {
                this.register(index, this.getPlayerStack(player), command);
            }

            index ++;
        }
    }
}
