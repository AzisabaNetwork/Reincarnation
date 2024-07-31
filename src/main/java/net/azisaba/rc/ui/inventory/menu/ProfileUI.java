package net.azisaba.rc.ui.inventory.menu;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Collections;

public class ProfileUI extends GameMenuUI
{

    public ProfileUI(Player player)
    {
        super(player, Component.text("プロフィール"));

        this.addSeparator();
        this.setFocusedTab(1);

        ItemStack progressStack = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta progressMeta = progressStack.getItemMeta();
        progressMeta.displayName(Component.text("進捗").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
        progressMeta.lore(Collections.singletonList(Component.text("Reincarnation での進捗…").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)));
        progressStack.setItemMeta(progressMeta);
        this.register(21, progressStack, "rc ui:progress " + player.getName());

        ItemStack levelStack = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta levelMeta = (SkullMeta) levelStack.getItemMeta();
        levelMeta.displayName(player.displayName().decoration(TextDecoration.ITALIC, false));
        levelMeta.setOwningPlayer(player);
        levelStack.setItemMeta(levelMeta);
        this.inventory.setItem(22, levelStack);

        ItemStack reportStack = new ItemStack(Material.PAPER);
        ItemMeta reportMeta = reportStack.getItemMeta();
        reportMeta.displayName(Component.text("報告").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
        reportMeta.lore(Collections.singletonList(Component.text("バグ・ルール違反などの報告").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)));
        reportStack.setItemMeta(reportMeta);
        this.inventory.setItem(23, reportStack);

        ItemStack donateStack = new ItemStack(Material.DIAMOND);
        ItemMeta donateMeta = donateStack.getItemMeta();
        donateMeta.displayName(Component.text("寄付").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false));
        donateMeta.lore(Collections.singletonList(Component.text("特別な報酬を獲得しましょう！").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)));
        donateStack.setItemMeta(donateMeta);
        this.inventory.setItem(29, donateStack);

        ItemStack temporaryStack = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta temporaryMeta = temporaryStack.getItemMeta();
        temporaryMeta.displayName(Component.text(""));
        temporaryStack.setItemMeta(temporaryMeta);

        this.inventory.setItem(30, temporaryStack);
        this.inventory.setItem(31, temporaryStack);
        this.inventory.setItem(32, temporaryStack);
        this.inventory.setItem(33, temporaryStack);
        this.inventory.setItem(39, temporaryStack);
        this.inventory.setItem(40, temporaryStack);
        this.inventory.setItem(41, temporaryStack);
    }
}
