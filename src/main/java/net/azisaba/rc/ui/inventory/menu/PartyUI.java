package net.azisaba.rc.ui.inventory.menu;

import net.azisaba.rc.quest.Party;
import net.azisaba.rc.util.PartyUtility;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Collections;

public class PartyUI extends GameMenuUI
{

    public PartyUI(Player player)
    {
        super(player, Component.text("パーティー"));

        this.addSeparator();
        this.setFocusedTab(3);

        if (! PartyUtility.isPartyPlayer(player))
        {
            ItemStack newPartyStack = new ItemStack(Material.IRON_SWORD);
            ItemMeta newPartyMeta = newPartyStack.getItemMeta();
            newPartyMeta.displayName(Component.text("新規作成").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
            newPartyMeta.lore(Collections.singletonList(Component.text("新しいパーティーをホストします").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)));
            newPartyMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            newPartyStack.setItemMeta(newPartyMeta);
            this.register(31, newPartyStack, "rc party:create " + player.getName());

            ItemStack closeStack = new ItemStack(Material.BARRIER);
            ItemMeta closeMeta = closeStack.getItemMeta();
            closeMeta.displayName(Component.text("閉じる").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false));
            closeStack.setItemMeta(closeMeta);
            this.register(49, closeStack, "rc ui:close " + player.getName());

            return;
        }

        Party party = PartyUtility.getParty(player);

        ItemStack leaderStack = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta leaderMeta = (SkullMeta) leaderStack.getItemMeta();
        leaderMeta.displayName(party.getLeader().displayName().decorate(TextDecoration.BOLD).decoration(TextDecoration.ITALIC, false));
        leaderMeta.setOwningPlayer(party.getLeader());
        leaderStack.setItemMeta(leaderMeta);
        this.inventory.setItem(18, leaderStack);

        ItemStack crownStack = new ItemStack(Material.DIAMOND_HELMET);
        ItemMeta crownMeta = crownStack.getItemMeta();
        crownMeta.displayName(Component.text("Party Leader!!").color(NamedTextColor.YELLOW).decorate(TextDecoration.BOLD).decoration(TextDecoration.ITALIC, false));
        crownMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
        crownMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        crownStack.setItemMeta(crownMeta);
        this.inventory.setItem(19, crownStack);

        if (party.getLeader() == this.player)
        {
            ItemStack closeStack = new ItemStack(Material.BARRIER);
            ItemMeta closeMeta = closeStack.getItemMeta();
            closeMeta.displayName(Component.text("Party を解散する").color(NamedTextColor.DARK_RED).decorate(TextDecoration.BOLD).decoration(TextDecoration.ITALIC, false));
            closeMeta.lore(Collections.singletonList(Component.text("クエストが進行中の場合は、クエストも終了されます").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)));
            closeStack.setItemMeta(closeMeta);
            this.register(20, closeStack, "rc party:close " + party.getId());
        }

        int index = 27;
        for (Player member : party.getMembers())
        {
            if (member == party.getLeader())
            {
                continue;
            }

            ItemStack memberStack = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta memberMeta = (SkullMeta) memberStack.getItemMeta();
            memberMeta.displayName(member.displayName().decoration(TextDecoration.ITALIC, false));
            memberMeta.setOwningPlayer(member);
            memberStack.setItemMeta(memberMeta);
            this.inventory.setItem(index, memberStack);

            if (party.getLeader() == this.player)
            {
                ItemStack kickStack = new ItemStack(Material.BARRIER);
                ItemMeta kickMeta = kickStack.getItemMeta();
                kickMeta.displayName(Component.text("Party から追放").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false));
                ArrayList<Component> kickLore = new ArrayList<>();
                kickLore.add(Component.text("この Party から追放します").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
                kickLore.add(Component.text("このプレイヤーには Party は解散されたと表示されます…").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false));
                kickMeta.lore(kickLore);
                kickStack.setItemMeta(kickMeta);
                this.register(index + 1, kickStack, "rc party:kick " + party.getId() + " " + member.getName());

                ItemStack assignmentStack = new ItemStack(Material.DIAMOND_HELMET);
                ItemMeta assignmentMeta = assignmentStack.getItemMeta();
                assignmentMeta.displayName(Component.text("Party を譲渡").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false));
                assignmentMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                assignmentStack.setItemMeta(assignmentMeta);
                this.register(index + 2, assignmentStack, "rc party:assignment " + party.getId() + " " + member.getName());
            }
            else if (member == this.player)
            {
                ItemStack quitStack = new ItemStack(Material.BARRIER);
                ItemMeta quitMeta = quitStack.getItemMeta();
                quitMeta.displayName(Component.text("Party を退出").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false));
                quitMeta.lore(Collections.singletonList(Component.text("Party を離脱します…").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)));
                quitStack.setItemMeta(quitMeta);
                this.register(index + 1, quitStack, "rc party:quit " + player.getName());
            }

            index += 9;
        }

        if (index < 54 && party.getLeader() == this.player)
        {
            ItemStack inviteStack = new ItemStack(Material.BOOK);
            ItemMeta inviteMeta = inviteStack.getItemMeta();
            inviteMeta.displayName(Component.text("プレイヤーを招待").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
            inviteMeta.lore(Collections.singletonList(Component.text("または…任意のプレイヤーを招待可能！").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)));
            inviteStack.setItemMeta(inviteMeta);
            this.register(index, inviteStack, "rc ui:party-invite " + player.getName());
        }
    }
}
