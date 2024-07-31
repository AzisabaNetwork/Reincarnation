package net.azisaba.rc.listener;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.azisaba.rc.Reincarnation;
import net.azisaba.rc.ui.AnimationBuilder;
import net.azisaba.rc.ui.SidePanel;
import net.azisaba.rc.user.User;
import net.azisaba.rc.util.PartyUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Date;

public class PlayerListener implements Listener
{

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        User user = User.getInstance(player);

        player.displayName(user.getRank().getRankedName(player));
        player.playerListName(user.getRank().getRankedName(player));

        SidePanel panel = new SidePanel();
        panel.addRows(8);
        panel.addRow(Component.text("www.azisaba.net").color(NamedTextColor.YELLOW));

        panel.setRunnable(new BukkitRunnable()
        {
            private final Component[] title = AnimationBuilder.HYPIXEL("  REINCARNATION  ");
            private final User user = User.getInstance(player);
            private int tick = 0;

            @Override
            public void run()
            {
                panel.setTitle(this.title[this.tick]);
                panel.setRow(0, Component.text(Reincarnation.sdf1.format(new Date())).color(NamedTextColor.GRAY));
                panel.setRow(2, Component.text("ランク: ").append(this.user.getRank().getDisplay()));
                panel.setRow(3, Component.text("所持金: ").append(Component.text(this.user.getMoney()).color(NamedTextColor.GREEN)));
                panel.setRow(4, Component.text("レベル: ").append(Component.text(this.user.getExp() / 10).color(NamedTextColor.GREEN)));
                panel.setRow(6, Component.text("ギルド: ").append(Component.text((this.user.getGuild() == null) ? "なし" : this.user.getGuild().getName()).color(NamedTextColor.GREEN)));

                this.tick = (this.title.length <= this.tick + 1) ? 0 : this.tick + 1;
            }
        }, 10L);

        panel.addPlayer(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();

        if (PartyUtil.isPartyPlayer(player))
        {
            PartyUtil.getParty(player).quit(player);
        }
    }

    @EventHandler
    public void onAsyncChat(AsyncChatEvent event)
    {
        Player player = event.getPlayer();
        User user = User.getInstance(player);

        event.setCancelled(true);
        Bukkit.broadcast(user.getRank().getRankedName(player).append(Component.text(": ").color(NamedTextColor.WHITE)).append(event.message().color(NamedTextColor.WHITE)));
    }
}
