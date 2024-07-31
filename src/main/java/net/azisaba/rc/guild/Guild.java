package net.azisaba.rc.guild;

import net.azisaba.rc.Reincarnation;
import net.azisaba.rc.user.User;
import net.azisaba.rc.util.GuildUtil;

import java.sql.*;
import java.util.ArrayList;

public class Guild
{
    private static final ArrayList<Guild> instances = new ArrayList<>();

    public static Guild getInstance(String id)
    {
        ArrayList<Guild> filteredInstances = new ArrayList<>(Guild.instances.stream().filter(i -> i.getId().equals(id)).toList());
        return filteredInstances.isEmpty() ? new Guild(id) : filteredInstances.get(0);
    }

    public static ArrayList<Guild> getInstances()
    {
        return Guild.instances;
    }

    private final String id;

    private String name;
    private int exp;

    private User master;
    private ArrayList<User> members = new ArrayList<>();

    public Guild(String id)
    {
        this.id = id;

        try
        {
            Connection con = DriverManager.getConnection(Reincarnation.DB_URL, Reincarnation.DB_USER, Reincarnation.DB_PASS);
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM guild WHERE id = ?");
            stmt.setString(1, this.id);
            ResultSet rs = stmt.executeQuery();

            rs.next();

            this.name = rs.getString("name");
            this.exp = rs.getInt("exp");
            this.master = User.getInstance(rs.getString("master"));

            rs.close();
            stmt.close();

            PreparedStatement stmt2 = con.prepareStatement("SELECT id FROM user WHERE guild = ?");
            stmt2.setString(1, this.id);
            ResultSet rs2 = stmt2.executeQuery();

            while (rs2.next())
            {
                User user = User.getInstance(rs2.getString("id"));
                this.members.add(user);
            }

            rs2.close();
            stmt2.close();
            con.close();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        Guild.instances.add(this);
        this.members.forEach(m -> m.setGuild(this));
    }

    public Guild(String name, User master)
    {
        this.id = GuildUtil.getId();
        this.name = name;
        this.master = master;
        this.exp = 0;

        try
        {
            Connection con = DriverManager.getConnection(Reincarnation.DB_URL, Reincarnation.DB_USER, Reincarnation.DB_PASS);
            PreparedStatement stmt = con.prepareStatement("INSERT INTO guild VALUES(?, ?, ?, ?)");
            stmt.setString(1, this.id);
            stmt.setString(2, this.name);
            stmt.setString(3, this.master.getId());
            stmt.setInt(4, this.exp);

            stmt.executeUpdate();

            stmt.close();
            con.close();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        Guild.instances.add(this);
        this.master.setGuild(this);
    }

    public String getId()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
        this.upload();
    }

    public User getMaster()
    {
        return this.master;
    }

    public void setMaster(User master)
    {
        this.master = master;
        this.upload();
    }

    public ArrayList<User> getMembers()
    {
        return this.members;
    }

    public void join(User user)
    {
        this.members.add(user);
        user.setGuild(this);
    }

    public void quit(User user)
    {
        this.members.remove(user);
        user.setGuild(this);
    }

    public void upload()
    {
        try
        {
            Connection con = DriverManager.getConnection(Reincarnation.DB_URL, Reincarnation.DB_USER, Reincarnation.DB_PASS);
            PreparedStatement stmt = con.prepareStatement("UPDATE guild SET name = ?, master = ?, exp = ? WHERE id = ?");
            stmt.setString(1, this.name);
            stmt.setString(2, this.master.getId());
            stmt.setInt(3, this.exp);
            stmt.setString(4, this.id);

            stmt.executeUpdate();

            stmt.close();
            con.close();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}
