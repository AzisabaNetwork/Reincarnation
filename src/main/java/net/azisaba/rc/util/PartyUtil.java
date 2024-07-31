package net.azisaba.rc.util;

import net.azisaba.rc.quest.Party;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class PartyUtil
{

    public static String getId()
    {
        String id = null;
        boolean loopFlag = true;

        while (loopFlag)
        {
            id = UUID.randomUUID().toString();
            loopFlag = Party.getInstance(id) != null;
        }

        return id;
    }

    public static Party getParty(Player player)
    {
        ArrayList<Party> filteredInstances = new ArrayList<>(Party.getInstances().stream().filter(p -> p.isMember(player)).toList());
        return filteredInstances.isEmpty() ? null : filteredInstances.get(0);
    }

    public static boolean isPartyPlayer(Player player)
    {
        return PartyUtil.getParty(player) != null;
    }
}
