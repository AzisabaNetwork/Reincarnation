package net.azisaba.rc.command;

import net.azisaba.rc.command.skill.RcCommandSkill;
import net.azisaba.rc.command.skill.guild.*;
import net.azisaba.rc.command.skill.party.*;
import net.azisaba.rc.command.skill.quest.QuestProgressionSkill;
import net.azisaba.rc.command.skill.quest.QuestStartSkill;
import net.azisaba.rc.command.skill.quest.QuestUnlockSkill;
import net.azisaba.rc.command.skill.scenario.ScenarioStartSkill;
import net.azisaba.rc.command.skill.social.SocialFriendRequestSkill;
import net.azisaba.rc.command.skill.social.SocialFriendSkill;
import net.azisaba.rc.command.skill.social.SocialUnfriendSkill;
import net.azisaba.rc.command.skill.ui.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RcCommand implements CommandExecutor, TabCompleter
{
    public static final HashMap<String, RcCommandSkill> skills = new HashMap<>();

    public RcCommand()
    {
        RcCommand.skills.put("guild:close", new GuildCloseSkill());
        RcCommand.skills.put("guild:create", new GuildCreateSkill());
        RcCommand.skills.put("guild:invite", new GuildInviteSkill());
        RcCommand.skills.put("guild:join", new GuildJoinSkill());
        RcCommand.skills.put("guild:quit", new GuildQuitSkill());
        RcCommand.skills.put("guild:rename", new GuildRenameSkill());
        RcCommand.skills.put("party:assignment", new PartyAssignmentSkill());
        RcCommand.skills.put("party:close", new PartyCloseSkill());
        RcCommand.skills.put("party:create", new PartyCreateSkill());
        RcCommand.skills.put("party:invite", new PartyInviteSkill());
        RcCommand.skills.put("party:join", new PartyJoinSkill());
        RcCommand.skills.put("party:kick", new PartyKickSkill());
        RcCommand.skills.put("party:quit", new PartyQuitSkill());
        RcCommand.skills.put("quest:progression", new QuestProgressionSkill());
        RcCommand.skills.put("quest:start", new QuestStartSkill());
        RcCommand.skills.put("quest:unlock", new QuestUnlockSkill());
        RcCommand.skills.put("scenario:start", new ScenarioStartSkill());
        RcCommand.skills.put("social:friend", new SocialFriendSkill());
        RcCommand.skills.put("social:friend-request", new SocialFriendRequestSkill());
        RcCommand.skills.put("social:unfriend", new SocialUnfriendSkill());
        RcCommand.skills.put("ui:close", new UICloseSkill());
        RcCommand.skills.put("ui:friend", new UIFriendSkill());
        RcCommand.skills.put("ui:friend-request", new UIFriendRequestSkill());
        RcCommand.skills.put("ui:guild", new UIGuildSkill());
        RcCommand.skills.put("ui:guild-invite", new UIGuildInviteSkill());
        RcCommand.skills.put("ui:guild-member", new UIGuildMemberSkill());
        RcCommand.skills.put("ui:party", new UIPartySkill());
        RcCommand.skills.put("ui:party-invite", new UIPartyInviteSkill());
        RcCommand.skills.put("ui:profile", new UIProfileSkill());
        RcCommand.skills.put("ui:quest", new UIQuestAllMenuSkill());
        RcCommand.skills.put("ui:quest-all", new UIQuestAllMenuSkill());
        RcCommand.skills.put("ui:quest-daily", new UIQuestDailyMenuSkill());
        RcCommand.skills.put("ui:quest-event", new UIQuestEventMenuSkill());
        RcCommand.skills.put("ui:quest-free", new UIQuestFreeMenuSkill());
        RcCommand.skills.put("ui:quest-story", new UIQuestStoryMenuSkill());
        RcCommand.skills.put("ui:settings", new UISettingsSkill());
        RcCommand.skills.put("ui:social-menu", new UISocialMenuSkill());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        if (args.length == 0)
        {
            sender.sendMessage(Component.text("Correct syntax: /rc <args…>").color(NamedTextColor.RED));
            return true;
        }

        if (! RcCommand.skills.containsKey(args[0]))
        {
            sender.sendMessage(Component.text(args[0] + " is an unknown skill.").color(NamedTextColor.RED));
            return true;
        }

        RcCommandSkill skill = RcCommand.skills.get(args[0]);

        if (! sender.isOp() && skill.isOPCommand())
        {
            sender.sendMessage(Component.text("You do not have sufficient permissions to execute the command.").color(NamedTextColor.RED));
            return true;
        }

        String[] args2 = new String[args.length - 1];
        System.arraycopy(args, 1, args2, 0, args.length - 1);
        skill.onCommand(sender, command, label, args2);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        if (! sender.isOp())
        {
            return null;
        }

        ArrayList<String> list = new ArrayList<>();

        if (args.length == 1)
        {
            list.addAll(RcCommand.skills.keySet().stream().filter(s -> s.startsWith(args[0])).toList());
            return list;
        }
        else if (RcCommand.skills.get(args[0]) != null)
        {
            String[] args2 = new String[args.length - 1];
            System.arraycopy(args, 1, args2, 0, args.length - 1);

            return RcCommand.skills.get(args[0]).onTabComplete(sender, command, args[0], args2);
        }
        else
        {
            return list;
        }
    }
}
