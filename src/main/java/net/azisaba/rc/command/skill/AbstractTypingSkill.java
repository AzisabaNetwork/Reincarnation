package net.azisaba.rc.command.skill;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public abstract class AbstractTypingSkill implements IRcCommandSkill
{
    private static final ArrayList<AbstractTypingSkill> instances = new ArrayList<>();

    public static AbstractTypingSkill getInstance(Player player)
    {
        ArrayList<AbstractTypingSkill> filteredInstances = new ArrayList<>(AbstractTypingSkill.instances.stream().filter(i -> i.player == player).toList());
        return filteredInstances.isEmpty() ? null : filteredInstances.get(0);
    }

    public Player player;

    public AbstractTypingSkill()
    {
        AbstractTypingSkill.instances.add(this);
    }

    @Override
    public void onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        this.enableTyping(this.player);
    }

    public void enableTyping(Player player)
    {
        if (this.player == null)
        {
            return;
        }

        player.addScoreboardTag("rc.typing");
    }

    public void onTyped(String string)
    {

    }
}
