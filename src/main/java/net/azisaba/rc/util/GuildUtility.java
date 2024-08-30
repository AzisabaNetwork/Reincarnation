package net.azisaba.rc.util;

import net.azisaba.rc.Reincarnation;
import net.azisaba.rc.guild.Guild;

import java.sql.*;
import java.util.Random;
import java.util.UUID;

public class GuildUtility
{
    public static String getTemporaryName()
    {
        boolean loopFlag = true;

        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        while (loopFlag)
        {
            sb = new StringBuilder();

            for (int i = 0; i < 8; i ++)
            {
                sb.append(characters.charAt(random.nextInt(characters.length())));
            }

            final StringBuilder finalSb = sb;
            loopFlag = Guild.getInstances().stream().anyMatch(i -> i.getName().contentEquals(finalSb));
        }

        return sb.toString();
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
                Guild.getInstance(UUID.fromString(rs.getString("id")));
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

    public static boolean exists(UUID id)
    {
        try
        {
            Connection con = DriverManager.getConnection(Reincarnation.DB_URL, Reincarnation.DB_USER, Reincarnation.DB_PASS);
            PreparedStatement stmt = con.prepareStatement("SELECT id FROM guild WHERE id = ?");
            stmt.setString(1, id.toString());
            stmt.executeUpdate();
            ResultSet rs = stmt.executeQuery();

            boolean exists = rs.next();

            rs.close();
            stmt.close();
            con.close();

            return exists;
        }
        catch (SQLException e)
        {
            return false;
        }
    }
}
