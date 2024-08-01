package net.azisaba.rc.listener;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.azisaba.rc.command.skill.AbstractTypingSkill;
import net.azisaba.rc.user.User;
import net.azisaba.rc.util.PartyUtil;
import net.azisaba.rc.util.UserUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener
{

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        User user = User.getInstance(player);

        player.displayName(user.getRankedName());
        player.playerListName(user.getRankedName());

        UserUtil.sidePanel(player);
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

        if (player.getScoreboardTags().contains("rc.typing"))
        {
            AbstractTypingSkill skill = AbstractTypingSkill.getInstance(player);
            String string = ((TextComponent) event.message()).content();
            player.sendMessage(Component.text(string).color(NamedTextColor.GRAY));

            if (skill != null)
            {
                skill.onTyped(string);
                skill.player = null;
            }

            player.removeScoreboardTag("rc.typing");
            event.setCancelled(true);
            return;
        }

        User user = User.getInstance(player);

        event.setCancelled(true);
        Bukkit.broadcast(user.getRankedName().append(Component.text(": ").color(NamedTextColor.WHITE)).append(event.message().color(NamedTextColor.WHITE)));
    }
}
