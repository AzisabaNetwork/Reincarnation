package net.azisaba.rc.ui.inventory.menu;

import net.azisaba.rc.guild.Guild;
import net.azisaba.rc.user.User;
import net.azisaba.rc.util.GuildUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

public class GuildUI extends GameMenuUI
{

    public GuildUI(Player player)
    {
        super(player, Component.text("ギルド"));

        this.addSeparator();
        this.setFocusedTab(4);

        User user = User.getInstance(player);

        if (user.getGuild() == null)
        {
            ItemStack newGuildStack = new ItemStack(Material.BOW);
            ItemMeta newGuildMeta = newGuildStack.getItemMeta();
            newGuildMeta.displayName(Component.text("新規作成 (10000 RI)").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
            newGuildMeta.lore(Collections.singletonList(Component.text("ギルドを創設する").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)));
            newGuildMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            newGuildStack.setItemMeta(newGuildMeta);
            this.register(31, newGuildStack, String.format("rc guild:create %s %s", GuildUtil.getTemporaryName(), player.getName()));

            ItemStack closeStack = new ItemStack(Material.COMPASS);
            ItemMeta closeMeta = closeStack.getItemMeta();
            closeMeta.displayName(Component.text("探索").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
            closeMeta.lore(Collections.singletonList(Component.text("または…オープンギルドを探してみましょう").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)));
            closeStack.setItemMeta(closeMeta);
            this.register(49, closeStack, "rc ui:guilds " + player.getName());
            return;
        }

        Guild guild = user.getGuild();

        ItemStack stack30 = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta30 = (SkullMeta) stack30.getItemMeta();
        meta30.displayName(Component.text(guild.getName()).color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false));

        ArrayList<Component> lore30 = new ArrayList<>();
        lore30.add(Component.text("マスター: ").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false)
                .append(guild.getMaster().getRankedName()));
        lore30.add(Component.text("メンバー: ").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false)
                .append(Component.text(guild.getMembers().size()).color(NamedTextColor.GREEN)));
        lore30.add(Component.text("Guild レベル: ").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false)
                .append(Component.text(guild.getExp() / 10).color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false)));
        meta30.lore(lore30);

        meta30.setOwningPlayer(Bukkit.getOfflinePlayer(UUID.fromString(guild.getMaster().getId())));
        stack30.setItemMeta(meta30);
        this.inventory.setItem(30, stack30);

        ItemStack stack31 = new ItemStack(Material.OAK_SIGN);
        ItemMeta meta31 = stack31.getItemMeta();
        meta31.displayName(Component.text("Guild メンバー").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
        ArrayList<User> online = new ArrayList<>(guild.getMembers().stream().filter(m -> Bukkit.getPlayerExact(m.getName()) != null).toList());
        ArrayList<Component> lore31 = new ArrayList<>();
        lore31.add(Component.text(online.size() + " 人のメンバーがオンラインです:").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false));
        online.forEach(o -> lore31.add(Component.text("- ").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)
                .append(Bukkit.getPlayer(UUID.fromString(o.getId())).displayName())));
        meta31.lore(lore31);
        stack31.setItemMeta(meta31);
        this.register(31, stack31, String.format("rc ui:guild-member %s %s", player.getName(), guild.getId()));

        ItemStack stack32 = new ItemStack(Material.BARRIER);
        ItemMeta meta32 = stack32.getItemMeta();
        if (user == guild.getMaster())
        {
            meta32.displayName(Component.text("Guild を解散").color(NamedTextColor.DARK_RED).decoration(TextDecoration.ITALIC, false));
            meta32.lore(Collections.singletonList(Component.text("この操作は取り消せません！").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false)));
            stack32.setItemMeta(meta32);
            this.register(32, stack32, String.format("rc guild:close %s %s", guild.getId(), player.getName()));
        }
        else
        {
            meta32.displayName(Component.text("Guild を脱退").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false));
            meta32.lore(Collections.singletonList(Component.text("この Guild から離れます…").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)));
            stack32.setItemMeta(meta32);
            this.register(32, stack32, String.format("rc guild:quit %s", player.getName()));
        }

        ItemStack stack39 = new ItemStack(Material.GOLD_INGOT);
        ItemMeta meta39 = stack39.getItemMeta();
        meta39.displayName(Component.text("財産").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
        meta39.lore(Collections.singletonList(Component.text("残高: ").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false)
                .append(Component.text(guild.getMoney() + " RI").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false))));
        stack39.setItemMeta(meta39);
        this.inventory.setItem(39, stack39);

        if (user != guild.getMaster())
        {
            return;
        }

        ItemStack stack40 = new ItemStack(Material.NAME_TAG);
        ItemMeta meta40 = stack40.getItemMeta();
        meta40.displayName(Component.text("名前の編集").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
        meta40.lore(Collections.singletonList(Component.text("Guild の名前を変更する").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)));
        stack40.setItemMeta(meta40);
        this.register(40, stack40, String.format("rc guild:rename %s %s", guild.getId(), player.getName()));
    }
}
