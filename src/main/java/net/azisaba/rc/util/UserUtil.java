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
            final Connection con = DriverManager.getConnection(Reincarnation.DB_URL, Reincarnation.DB_USER, Reincarnation.DB_PASS);
            final PreparedStatement stmt = con.prepareStatement("SELECT name FROM user WHERE id = ?");
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            final boolean exits = rs.next();

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
            final Connection con = DriverManager.getConnection(Reincarnation.DB_URL, Reincarnation.DB_USER, Reincarnation.DB_PASS);
            final PreparedStatement stmt = con.prepareStatement("SELECT id FROM user");
            final ResultSet rs = stmt.executeQuery();

            while (rs.next())
            {
                User.getInstance(rs.getString("id"));
            }

            rs.close();
            stmt.close();

            final PreparedStatement stmt2 = con.prepareStatement("SELECT * FROM friend");
            final ResultSet rs2 = stmt2.executeQuery();

            while (rs2.next())
            {
                final User user1 = User.getInstance(rs2.getString("user1"));
                final User user2 = User.getInstance(rs2.getString("user2"));

                user1.getFriends().add(user2);
                user2.getFriends().add(user1);
            }

            rs2.close();
            stmt2.close();

            con.close();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}
