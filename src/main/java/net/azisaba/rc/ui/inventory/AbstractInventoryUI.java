package net.azisaba.rc.ui.inventory;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public abstract class AbstractInventoryUI
{
    protected static final HashMap<AbstractInventoryUI, Inventory> instances = new HashMap<>();

    public static HashMap<AbstractInventoryUI, Inventory> getInstances()
    {
        return AbstractInventoryUI.instances;
    }

    protected final Player player;
    protected final Inventory inventory;
    protected final HashMap<Integer, String> items = new HashMap<>();

    public AbstractInventoryUI(Player player, Inventory inventory)
    {
        this.player = player;
        this.inventory = inventory;

        this.player.openInventory(this.inventory);
        AbstractInventoryUI.instances.put(this, inventory);
    }

    public void register(int index, ItemStack item, String command)
    {
        this.items.put(index, command);
        this.inventory.setItem(index,item);
    }

    public void onInventoryClick(InventoryClickEvent event)
    {
        if (event.getCurrentItem() != null && this.items.containsKey(event.getSlot()))
        {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.items.get(event.getSlot()));
        }
    }

    public void onInventoryClose(InventoryCloseEvent event)
    {
        AbstractInventoryUI.instances.remove(this);
    }
}
