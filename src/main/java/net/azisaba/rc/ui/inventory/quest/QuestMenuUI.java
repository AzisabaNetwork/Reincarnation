package net.azisaba.rc.ui.inventory.quest;

import net.azisaba.rc.quest.QuestEngine;
import net.azisaba.rc.ui.inventory.InventoryUI;
import net.azisaba.rc.user.User;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;

public abstract class QuestMenuUI extends InventoryUI
{
    private final ArrayList<QuestEngine> quests = new ArrayList<>();
    protected int page = 0;

    public QuestMenuUI(Player player)
    {
        super(player, Bukkit.createInventory(null, 54, Component.text("クエスト")));

        ItemStack stack2 = new ItemStack(Material.BOOK);
        ItemMeta meta2 = stack2.getItemMeta();
        meta2.displayName(Component.text("すべて").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
        meta2.lore(Collections.singletonList(Component.text("現在受注可能なすべて").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)));
        stack2.setItemMeta(meta2);
        this.register(2, stack2, "rc ui:quest-all " + player.getName());

        ItemStack stack3 = new ItemStack(Material.SPYGLASS);
        ItemMeta meta3 = stack3.getItemMeta();
        meta3.displayName(Component.text("ストーリー").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
        meta3.lore(Collections.singletonList(Component.text("Reincarnation のストーリーに関わる").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)));
        stack3.setItemMeta(meta3);
        this.register(3, stack3, "rc ui:quest-story " + player.getName());

        ItemStack stack4 = new ItemStack(Material.CLOCK);
        ItemMeta meta4 = stack4.getItemMeta();
        meta4.displayName(Component.text("デイリー").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
        meta4.lore(Collections.singletonList(Component.text("午前4時にリセットされる…").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)));
        stack4.setItemMeta(meta4);
        this.register(4, stack4, "rc ui:quest-daily " + player.getName());

        ItemStack stack5 = new ItemStack(Material.DIAMOND);
        ItemMeta meta5 = stack5.getItemMeta();
        meta5.displayName(Component.text("イベント").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
        meta5.lore(Collections.singletonList(Component.text("期間限定クエスト!!").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false)));
        stack5.setItemMeta(meta5);
        this.register(5, stack5, "rc ui:quest-event " + player.getName());

        ItemStack stack6 = new ItemStack(Material.WHEAT);
        ItemMeta meta6 = stack6.getItemMeta();
        meta6.displayName(Component.text("フリー").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
        meta6.lore(Collections.singletonList(Component.text("その他のクエスト").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)));
        stack6.setItemMeta(meta6);
        this.register(6, stack6, "rc ui:quest-free " + player.getName());

        ItemStack backStack = new ItemStack(Material.ARROW);
        ItemMeta backMeta = backStack.getItemMeta();
        backMeta.displayName(Component.text("戻る…").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
        backStack.setItemMeta(backMeta);
        this.inventory.setItem(28, backStack);

        ItemStack nextStack = new ItemStack(Material.ARROW);
        ItemMeta nextMeta = nextStack.getItemMeta();
        nextMeta.displayName(Component.text("次へ…").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
        nextStack.setItemMeta(nextMeta);
        this.inventory.setItem(34, nextStack);
    }

    public void addSeparator()
    {
        ItemStack separatorStack = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
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

    public ArrayList<QuestEngine> getQuests()
    {
        return this.quests;
    }

    public void setQuests(ArrayList<QuestEngine> quests)
    {
        User user = User.getInstance(this.player);
        this.quests.clear();
        this.quests.addAll(new ArrayList<>(quests.stream().filter(user::isUnlocked).toList()));
    }

    public void rendering(int page)
    {
        int x = 2, y = 2;
        for (int i = page * 15; i < (page + 1) * 15; i ++)
        {
            if (this.quests.size() <= i)
            {
                break;
            }

            QuestEngine quest = this.quests.get(i);

            ItemStack questStack = new ItemStack(quest.getFavicon());
            ItemMeta questMeta = questStack.getItemMeta();

            questMeta.setDisplayName("§r§l" + quest.getDisplay());

            ArrayList<String> lore = new ArrayList<>();
            quest.getLore().forEach(l -> lore.add("§r§7" + l));
            lore.add("");
            switch (quest.getType())
            {
                case DAILY -> lore.add("§3§lDaily");
                case EVENT -> lore.add("§d§lEvent");
                case FREE -> lore.add("§b§lFree");
                case STORY -> lore.add("§a§lStory");
            }
            questMeta.setLore(lore);

            questMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

            questStack.setItemMeta(questMeta);
            this.register(y * 9 + x, questStack, String.format("rc quest:start %s %s", quest.getId(), player.getName()));

            if (x == 6)
            {
                x = 2;
                y ++;
            }
            else
            {
                x ++;
            }
        }
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event)
    {
        super.onInventoryClick(event);

        if (event.getSlot() == 28)
        {
            this.page -= (0 < this.page) ? 1 : 0;
            this.rendering(this.page);
        }

        if (event.getSlot() == 34 && this.inventory.getItem(42) != null && this.page < (this.quests.size() / 15))
        {
            this.page ++;
            this.rendering(this.page);
        }
    }
}
