package net.azisaba.rc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLUtil
{

    public static boolean test(String url, String user, String pass)
    {
        try
        {
            Connection con = DriverManager.getConnection(url, user, pass);
            PreparedStatement stmt = con.prepareStatement("SHOW DATABASES");

            stmt.executeUpdate();
            stmt.close();
            con.close();
            return true;
        }
        catch (SQLException e)
        {
            return false;
        }
    }
}
