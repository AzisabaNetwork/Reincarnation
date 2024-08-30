package net.azisaba.rc.command;

import net.azisaba.rc.command.skill.ICommandSkill;
import net.azisaba.rc.command.skill.guild.*;
import net.azisaba.rc.command.skill.party.*;
import net.azisaba.rc.command.skill.quest.QuestProgressionSkill;
import net.azisaba.rc.command.skill.quest.QuestStartSkill;
import net.azisaba.rc.command.skill.quest.QuestUnlockSkill;
import net.azisaba.rc.command.skill.scenario.ScenarioStartSkill;
import net.azisaba.rc.command.skill.social.*;
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
    public static final HashMap<String, ICommandSkill> skills = new HashMap<>();

    public static void registerSkill(ICommandSkill skill)
    {
        RcCommand.skills.put(skill.getName(), skill);
    }

    static
    {
        RcCommand.registerSkill(new GuildCloseSkill());
        RcCommand.registerSkill(new GuildCreateSkill());
        RcCommand.registerSkill(new GuildInviteSkill());
        RcCommand.registerSkill(new GuildJoinSkill());
        RcCommand.registerSkill(new GuildQuitSkill());
        RcCommand.registerSkill(new GuildRenameSkill());
        RcCommand.registerSkill(new PartyAssignmentSkill());
        RcCommand.registerSkill(new PartyCloseSkill());
        RcCommand.registerSkill(new PartyCreateSkill());
        RcCommand.registerSkill(new PartyInviteSkill());
        RcCommand.registerSkill(new PartyJoinSkill());
        RcCommand.registerSkill(new PartyKickSkill());
        RcCommand.registerSkill(new PartyQuitSkill());
        RcCommand.registerSkill(new QuestProgressionSkill());
        RcCommand.registerSkill(new QuestStartSkill());
        RcCommand.registerSkill(new QuestUnlockSkill());
        RcCommand.registerSkill(new ScenarioStartSkill());
        RcCommand.registerSkill(new SocialDiscordSkill());
        RcCommand.registerSkill(new SocialFriendSkill());
        RcCommand.registerSkill(new SocialFriendRequestSkill());
        RcCommand.registerSkill(new SocialTwitterSkill());
        RcCommand.registerSkill(new SocialUnfriendSkill());
        RcCommand.registerSkill(new SocialYouTubeSkill());
        RcCommand.registerSkill(new UICloseSkill());
        RcCommand.registerSkill(new UIFriendSkill());
        RcCommand.registerSkill(new UIFriendRequestSkill());
        RcCommand.registerSkill(new UIGuildSkill());
        RcCommand.registerSkill(new UIGuildInviteSkill());
        RcCommand.registerSkill(new UIGuildMemberSkill());
        RcCommand.registerSkill(new UIPartySkill());
        RcCommand.registerSkill(new UIPartyInviteSkill());
        RcCommand.registerSkill(new UIMyProfileSkill());
        RcCommand.registerSkill(new UIQuestAllMenuSkill());
        RcCommand.registerSkill(new UIQuestAllMenuSkill());
        RcCommand.registerSkill(new UIQuestDailyMenuSkill());
        RcCommand.registerSkill(new UIQuestEventMenuSkill());
        RcCommand.registerSkill(new UIQuestFreeMenuSkill());
        RcCommand.registerSkill(new UIQuestStoryMenuSkill());
        RcCommand.registerSkill(new UISettingsSkill());
        RcCommand.registerSkill(new UIProfileSkill());
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

        ICommandSkill skill = RcCommand.skills.get(args[0]);

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
