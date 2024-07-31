package net.azisaba.rc.util;

import net.azisaba.rc.Reincarnation;
import net.azisaba.rc.user.User;

import java.sql.*;

public class UserUtil
{

    public static boolean exits(String id)
    {
        try
        {
            Connection con = DriverManager.getConnection(Reincarnation.DB_URL, Reincarnation.DB_USER, Reincarnation.DB_PASS);
            PreparedStatement stmt = con.prepareStatement("SELECT name FROM user WHERE id = ?");
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            boolean exits = rs.next();

            rs.close();
            stmt.close();
            con.close();

            return exits;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void mount()
    {
        try
        {
            Connection con = DriverManager.getConnection(Reincarnation.DB_URL, Reincarnation.DB_USER, Reincarnation.DB_PASS);
            PreparedStatement stmt = con.prepareStatement("SELECT id FROM user");
            ResultSet rs = stmt.executeQuery();

            while (rs.next())
            {
                User.getInstance(rs.getString("id"));
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
