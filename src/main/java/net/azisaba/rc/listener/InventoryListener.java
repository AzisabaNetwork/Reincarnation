package net.azisaba.rc.listener;

import net.azisaba.rc.ui.inventory.AbstractInventoryUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.Map;

public class InventoryListener implements Listener
{

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event)
    {
        Inventory inventory = event.getClickedInventory();
        Player player = (Player) event.getWhoClicked();

        if (AbstractInventoryUI.getInstances().containsValue(player.getOpenInventory().getTopInventory()))
        {
            event.setCancelled(true);

            if (AbstractInventoryUI.getInstances().containsValue(inventory))
            {
                Map.Entry<AbstractInventoryUI, Inventory> entry = AbstractInventoryUI.getInstances().entrySet().stream().filter(i -> i.getValue() == inventory).toList().get(0);
                entry.getKey().onInventoryClick(event);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event)
    {
        Inventory inventory = event.getInventory();

        if (AbstractInventoryUI.getInstances().containsValue(inventory))
        {
            Map.Entry<AbstractInventoryUI, Inventory> entry = AbstractInventoryUI.getInstances().entrySet().stream().filter(i -> i.getValue() == inventory).toList().get(0);
            entry.getKey().onInventoryClose(event);
        }
    }
}
