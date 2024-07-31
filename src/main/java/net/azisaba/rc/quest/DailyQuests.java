package net.azisaba.rc.quest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class DailyQuests
{
    public static final ArrayList<QuestEngine> quests = new ArrayList<>();

    public static void shuffle()
    {
        ArrayList<QuestEngine> dailyQuests = new ArrayList<>(QuestEngine.getInstances().stream().filter(q -> q.getType() == QuestType.DAILY).toList());
        Collections.shuffle(dailyQuests, new Random());

        DailyQuests.quests.clear();
        DailyQuests.quests.addAll(dailyQuests.subList(0, Math.min(6, dailyQuests.size())));
    }
}
