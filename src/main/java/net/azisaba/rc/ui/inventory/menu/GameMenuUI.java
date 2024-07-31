package net.azisaba.rc.ui.inventory.menu;

import net.azisaba.rc.ui.inventory.InventoryUI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Collections;

public abstract class GameMenuUI extends InventoryUI
{

    public GameMenuUI(Player player, Component title)
    {
        super(player, Bukkit.createInventory(null, 54, title));

        ItemStack stack2 = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta2 = (SkullMeta) stack2.getItemMeta();
        meta2.displayName(Component.text("プロフィール").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
        meta2.lore(Collections.singletonList(Component.text("Reincarnationでのアジ鯖プロフィールの編集").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)));
        meta2.setOwningPlayer(player);
        stack2.setItemMeta(meta2);
        this.register(2, stack2, "rc ui:profile " + player.getName());

        ItemStack stack3 = new ItemStack(Material.COOKIE);
        ItemMeta meta3 = stack3.getItemMeta();
        meta3.displayName(Component.text("フレンド").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
        meta3.lore(Collections.singletonList(Component.text("フレンドの申請、削除など").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)));
        stack3.setItemMeta(meta3);
        this.register(3, stack3, "rc ui:friend " + player.getName());

        ItemStack stack4 = new ItemStack(Material.IRON_SWORD);
        ItemMeta meta4 = stack4.getItemMeta();
        meta4.displayName(Component.text("パーティー").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
        meta4.lore(Collections.singletonList(Component.text("パーティーの作成と簡単な管理").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)));
        meta4.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        stack4.setItemMeta(meta4);
        this.register(4, stack4, "rc ui:party " + player.getName());

        ItemStack stack5 = new ItemStack(Material.BOW);
        ItemMeta meta5 = stack5.getItemMeta();
        meta5.displayName(Component.text("ギルド").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
        meta5.lore(Collections.singletonList(Component.text("参加、招待…または創設！").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)));
        meta5.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        stack5.setItemMeta(meta5);
        this.register(5, stack5, "rc ui:guild " + player.getName());

        ItemStack stack6 = new ItemStack(Material.COMPARATOR);
        ItemMeta meta6 = stack6.getItemMeta();
        meta6.displayName(Component.text("設定").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
        meta6.lore(Collections.singletonList(Component.text("設定を編集します…").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)));
        stack6.setItemMeta(meta6);
        this.register(6, stack6, "rc ui:settings " + player.getName());
    }

    public void addSeparator()
    {
        ItemStack separatorStack = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta separatorMeta = separatorStack.getItemMeta();
        separatorMeta.displayName(Component.text(""));
        separatorStack.setItemMeta(separatorMeta);

        for (int i = 9; i < 18; i ++)
        {
            this.inventory.setItem(i, separatorStack);
        }
    }

    public void setFocusedTab(int index)
    {
        ItemStack tabStack = this.inventory.getItem(1 + index);
        ItemMeta tabMeta = tabStack.getItemMeta();

        tabMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
        tabMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);

        tabStack.setItemMeta(tabMeta);
    }
}
