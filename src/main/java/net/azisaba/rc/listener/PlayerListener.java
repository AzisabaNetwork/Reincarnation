package net.azisaba.rc.listener;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.azisaba.rc.command.skill.TypingSkill;
import net.azisaba.rc.scenario.Scenario;
import net.azisaba.rc.scenario.task.ScenarioTask;
import net.azisaba.rc.scenario.task.SelectTask;
import net.azisaba.rc.user.User;
import net.azisaba.rc.util.PartyUtility;
import net.azisaba.rc.util.UserUtility;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

public class PlayerListener implements Listener
{
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        User user = User.getInstance(player);

        player.displayName(user.getRankedName());
        player.playerListName(user.getRankedName());

        UserUtility.sidePanel(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();

        if (PartyUtility.isPartyPlayer(player))
        {
            PartyUtility.getParty(player).quit(player);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();
        Scenario scenario = Scenario.getInstance(player);

        if (scenario != null)
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        Scenario scenario = Scenario.getInstance(player);

        if (scenario != null)
        {
            scenario.onPlayerInteract(event);
        }
    }

    @EventHandler
    public void onPlayerJump(PlayerJumpEvent event)
    {
        Player player = event.getPlayer();
        Scenario scenario = Scenario.getInstance(player);

        if (scenario == null)
        {
            return;
        }

        ScenarioTask task = scenario.getCurrentTask();

        if (task instanceof SelectTask)
        {
            ((SelectTask) task).onPlayerJump(event);
        }
    }

    @EventHandler
    public void onPlayerShift(PlayerToggleSneakEvent event)
    {
        Player player = event.getPlayer();
        Scenario scenario = Scenario.getInstance(player);

        if (scenario == null)
        {
            return;
        }

        ScenarioTask task = scenario.getCurrentTask();

        if (task instanceof SelectTask)
        {
            ((SelectTask) task).onPlayerShift(event);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onAsyncChat(AsyncChatEvent event)
    {
        Player player = event.getPlayer();

        if (! player.getScoreboardTags().contains("rc.typing"))
        {
            return;
        }

        TypingSkill skill = TypingSkill.getInstance(player);
        String string = ((TextComponent) event.message()).content();
        player.sendMessage(Component.text(string).color(NamedTextColor.GRAY));

        if (skill != null)
        {
            skill.onTyped(string);
            skill.player = null;
        }

        player.removeScoreboardTag("rc.typing");
        event.setCancelled(true);
    }
}
