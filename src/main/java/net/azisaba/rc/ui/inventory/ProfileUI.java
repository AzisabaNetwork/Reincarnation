package net.azisaba.rc.ui.inventory;

import net.azisaba.rc.guild.Guild;
import net.azisaba.rc.user.User;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
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
import java.util.List;

public class ProfileUI extends InventoryUI
{
    public ProfileUI(Player player, User view)
    {
        super(player, Bukkit.createInventory(null, 45, view.getName()));
        
        User user = User.getInstance(player);

        ItemStack friendStack = new ItemStack(user.isFriend(view) ? Material.REDSTONE : Material.DIAMOND);
        ItemMeta friendMeta = friendStack.getItemMeta();
        friendMeta.displayName(Component.text(user.isFriend(view) ? "フレンドを削除" : "フレンドを申請").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
        friendStack.setItemMeta(friendMeta);
        this.addListener(12, friendStack, user.isFriend(view) ? String.format("rc social:unfriend %s %s", player.getName(), view.getName()) : String.format("rc social:friend-request %s %s", player.getName(), view.getName()));

        ItemStack headStack = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta headMeta = (SkullMeta) headStack.getItemMeta();
        headMeta.displayName(view.getRankedName());
        headMeta.setOwningPlayer(view.getAsOfflinePlayer());
        headStack.setItemMeta(headMeta);
        this.inventory.setItem(13, headStack);

        ItemStack reportStack = new ItemStack(Material.PAPER);
        ItemMeta reportMeta = reportStack.getItemMeta();
        reportMeta.displayName(Component.text("報告").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
        reportMeta.lore(Collections.singletonList(Component.text("ルール違反ですか？レポートを作成しましょう…").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)));
        reportStack.setItemMeta(reportMeta);
        this.inventory.setItem(14, reportStack);

        ItemStack youtubeStack = new ItemStack(Material.STICK);
        ItemMeta youtubeMeta = youtubeStack.getItemMeta();
        youtubeMeta.displayName(Component.text("YouTube").color(TextColor.color(255, 0, 0)).decoration(TextDecoration.ITALIC, false));
        youtubeMeta.lore(Collections.singletonList(Component.text((view.getYoutube() != null) ? String.format("@%s", view.getYoutube()) : String.format("%s はこの項目を設定していません", view.getName())).color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)));
        youtubeMeta.setCustomModelData(5);
        youtubeStack.setItemMeta(youtubeMeta);
        this.inventory.setItem(21, youtubeStack);

        ItemStack twitterStack = new ItemStack(Material.STICK);
        ItemMeta twitterMeta = twitterStack.getItemMeta();
        twitterMeta.displayName(Component.text("Twitter").color(TextColor.color(29, 161, 242)).decoration(TextDecoration.ITALIC, false));
        twitterMeta.lore(Collections.singletonList(Component.text((view.getTwitter() != null) ? String.format("@%s", view.getTwitter()) : String.format("%s はこの項目を設定していません", view.getName())).color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)));
        twitterMeta.setCustomModelData(6);
        twitterStack.setItemMeta(twitterMeta);
        this.inventory.setItem(22, twitterStack);

        ItemStack discordStack = new ItemStack(Material.STICK);
        ItemMeta discordMeta = discordStack.getItemMeta();
        discordMeta.displayName(Component.text("Discord").color(TextColor.color(88, 101, 242)).decoration(TextDecoration.ITALIC, false));
        discordMeta.lore(Collections.singletonList(Component.text((view.getDiscord() != null) ? view.getDiscord() : String.format("%s はこの項目を設定していません", view.getName())).color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)));
        discordMeta.setCustomModelData(7);
        discordStack.setItemMeta(discordMeta);
        this.inventory.setItem(23, discordStack);

        ItemStack tradeStack = new ItemStack(Material.CHEST);
        ItemMeta tradeMeta = tradeStack.getItemMeta();
        tradeMeta.displayName(Component.text("Trade").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
        tradeMeta.lore(List.of(Component.text("Trade 申請を送信します").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)));
        tradeStack.setItemMeta(tradeMeta);
        this.inventory.setItem(30, tradeStack);

        ItemStack closeStack = new ItemStack(Material.OAK_DOOR);
        ItemMeta closeMeta = closeStack.getItemMeta();
        closeMeta.displayName(Component.text("閉じる").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false));
        closeMeta.lore(List.of(Component.text("この画面を閉じます").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)));
        closeStack.setItemMeta(closeMeta);
        this.addListener(31, closeStack, "rc ui:close " + this.player.getName());

        Guild guild = user.getGuild();

        if (guild != null)
        {
            ItemStack guildStack = new ItemStack(guild.isMember(view) ? Material.HOPPER : Material.BOW);
            ItemMeta guildMeta = guildStack.getItemMeta();
            guildMeta.displayName(Component.text(guild.isMember(view) ? "Guild から追放" : "Guild に招待").color(guild.isMember(view) ? NamedTextColor.RED : NamedTextColor.GREEN));
            guildStack.setItemMeta(guildMeta);
            this.addListener(32, guildStack, guild.isMember(view) ? String.format("rc guild:kick %s", view.getName()) : String.format("rc guild:invite %s %s", guild.getId(), view.getName()));
        }
        else
        {
            ItemStack guildStack = new ItemStack(Material.BOW);
            ItemMeta guildMeta = guildStack.getItemMeta();
            guildMeta.displayName(Component.text("Guild に招待").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
            guildMeta.lore(List.of(Component.text("まずは Guild を作成しましょう…！").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)));
            guildMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
            guildMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            guildStack.setItemMeta(guildMeta);
            this.addListener(32, guildStack, "rc ui:guild " + player.getName());
        }
    }
}
