package net.azisaba.rc.util;

import net.azisaba.rc.Reincarnation;
import net.azisaba.rc.guild.Guild;

import java.sql.*;
import java.util.UUID;

public class GuildUtil
{

    public static String getId()
    {
        String id = null;
        boolean loopFlag = true;

        while (loopFlag)
        {
            id = UUID.randomUUID().toString();
            String finalId = id;
            loopFlag = ! Guild.getInstances().stream().filter(i -> i.getId().equals(finalId)).toList().isEmpty();
        }

        return id;
    }

    public static void mount()
    {
        try
        {
            Connection con = DriverManager.getConnection(Reincarnation.DB_URL, Reincarnation.DB_USER, Reincarnation.DB_PASS);
            PreparedStatement stmt = con.prepareStatement("SELECT id FROM guild");
            ResultSet rs = stmt.executeQuery();

            while (rs.next())
            {
                Guild.getInstance(rs.getString("id"));
            }

            rs.close();
            stmt.close();
            con.close();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}
