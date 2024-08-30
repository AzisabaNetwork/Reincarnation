package net.azisaba.rc.ui.inventory.gamemenu;

import net.azisaba.rc.user.User;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.Collections;

public class MyProfileUI extends GameMenuUI
{
    public MyProfileUI(Player player)
    {
        super(player, Component.text("My Profile"));

        this.addSeparator();
        this.setFocusedTab(1);

        User user = User.getInstance(player);

        ItemStack progressStack = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta progressMeta = progressStack.getItemMeta();
        progressMeta.displayName(Component.text("進捗").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
        progressMeta.lore(Collections.singletonList(Component.text("Reincarnation での進捗…").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)));
        progressStack.setItemMeta(progressMeta);
        this.addListener(21, progressStack, "rc ui:progress " + player.getName());

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

        ItemStack youtubeStack = new ItemStack(Material.STICK);
        ItemMeta youtubeMeta = youtubeStack.getItemMeta();
        youtubeMeta.displayName(Component.text("YouTube").color(TextColor.color(255, 0, 0)).decoration(TextDecoration.ITALIC, false));
        youtubeMeta.lore(Arrays.asList(Component.text("YouTube アカウントをプロフィールと紐づけます").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false),
                Component.text(String.format("現在の設定: @%s", (user.getYoutube() == null) ? "未設定" : user.getYoutube())).color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)));
        youtubeMeta.setCustomModelData(5);
        youtubeStack.setItemMeta(youtubeMeta);
        this.addListener(30, youtubeStack, "rc social:youtube " + player.getName());

        ItemStack twitterStack = new ItemStack(Material.STICK);
        ItemMeta twitterMeta = twitterStack.getItemMeta();
        twitterMeta.displayName(Component.text("Twitter").color(TextColor.color(29, 161, 242)).decoration(TextDecoration.ITALIC, false));
        twitterMeta.lore(Arrays.asList(Component.text("Twitter アカウントをプロフィールと紐づけます").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false),
                Component.text(String.format("現在の設定: @%s", (user.getTwitter() == null) ? "未設定" : user.getTwitter())).color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)));
        twitterMeta.setCustomModelData(6);
        twitterStack.setItemMeta(twitterMeta);
        this.addListener(31, twitterStack, "rc social:twitter " + player.getName());

        ItemStack discordStack = new ItemStack(Material.STICK);
        ItemMeta discordMeta = discordStack.getItemMeta();
        discordMeta.displayName(Component.text("Discord").color(TextColor.color(88, 101, 242)).decoration(TextDecoration.ITALIC, false));
        discordMeta.lore(Arrays.asList(Component.text("Discord アカウントをプロフィールと紐づけます").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false),
                Component.text(String.format("現在の設定: %s", (user.getDiscord() == null) ? "未設定" : user.getYoutube())).color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)));
        discordMeta.setCustomModelData(7);
        discordStack.setItemMeta(discordMeta);
        this.addListener(32, discordStack, "rc social:discord " + player.getName());

        ItemStack temporaryStack = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta temporaryMeta = temporaryStack.getItemMeta();
        temporaryMeta.displayName(Component.text(""));
        temporaryStack.setItemMeta(temporaryMeta);

        this.inventory.setItem(33, temporaryStack);
        this.inventory.setItem(39, temporaryStack);
        this.inventory.setItem(40, temporaryStack);
        this.inventory.setItem(41, temporaryStack);
    }
}
