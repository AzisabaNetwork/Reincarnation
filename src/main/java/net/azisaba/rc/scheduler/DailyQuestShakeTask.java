package net.azisaba.rc.scheduler;

import net.azisaba.rc.Reincarnation;
import net.azisaba.rc.quest.DailyQuests;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DailyQuestShakeTask extends BukkitRunnable
{
    public static long getLaterTicks()
    {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.systemDefault());
        ZonedDateTime next4AM = now.withHour(4).withMinute(0).withSecond(0).withNano(0);

        if (now.isAfter(next4AM))
        {
            next4AM = next4AM.plusDays(1);
        }

        long millisecondsUntil4AM = Duration.between(now, next4AM).toMillis();
        return millisecondsUntil4AM / 50;
    }

    @Override
    public void run()
    {
        DailyQuests.shuffle();
        new DailyQuestShakeTask().runTaskLater(Reincarnation.getPlugin(), DailyQuestShakeTask.getLaterTicks());
    }
}
