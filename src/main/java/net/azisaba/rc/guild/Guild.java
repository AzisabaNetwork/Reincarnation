package net.azisaba.rc.guild;

import net.azisaba.rc.Reincarnation;
import net.azisaba.rc.user.User;
import net.azisaba.rc.util.GuildUtility;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class Guild
{
    private static final ArrayList<Guild> instances = new ArrayList<>();

    public static Guild getInstance(UUID id)
    {
        ArrayList<Guild> filteredInstances = new ArrayList<>(Guild.instances.stream().filter(i -> i.getId().equals(id)).toList());
        return filteredInstances.isEmpty() ? GuildUtility.exists(id) ? new Guild(id) : null : filteredInstances.get(0);
    }

    public static ArrayList<Guild> getInstances()
    {
        return Guild.instances;
    }

    private final UUID id;

    private String name;
    private int exp;
    private int money;

    private User master;
    private ArrayList<User> members = new ArrayList<>();

    public Guild(UUID id)
    {
        this.id = id;

        try
        {
            Connection con = DriverManager.getConnection(Reincarnation.DB_URL, Reincarnation.DB_USER, Reincarnation.DB_PASS);
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM guild WHERE id = ?");
            stmt.setString(1, this.id.toString());
            ResultSet rs = stmt.executeQuery();

            rs.next();

            this.name = rs.getString("name");
            this.exp = rs.getInt("exp");
            this.money = rs.getInt("money");
            this.master = User.getInstance(UUID.fromString(rs.getString("master")));

            rs.close();
            stmt.close();

            PreparedStatement stmt2 = con.prepareStatement("SELECT id FROM user WHERE guild = ?");
            stmt2.setString(1, this.id.toString());
            ResultSet rs2 = stmt2.executeQuery();

            while (rs2.next())
            {
                User user = User.getInstance(UUID.fromString(rs2.getString("id")));
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
        this.id = UUID.randomUUID();
        this.name = name;
        this.master = master;
        this.exp = 0;

        try
        {
            Connection con = DriverManager.getConnection(Reincarnation.DB_URL, Reincarnation.DB_USER, Reincarnation.DB_PASS);
            PreparedStatement stmt = con.prepareStatement("INSERT INTO guild VALUES(?, ?, ?, ?, 0)");
            stmt.setString(1, this.id.toString());
            stmt.setString(2, this.name);
            stmt.setString(3, this.master.getId().toString());
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
        this.members.add(master);
        this.master.setGuild(this);
    }

    public UUID getId()
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

    public boolean isMember(Player player)
    {
        return this.isMember(User.getInstance(player));
    }

    public boolean isMember(User user)
    {
        return this.members.contains(user);
    }

    public int getExp()
    {
        return this.exp;
    }

    public void setExp(int exp)
    {
        this.exp = exp;
        this.upload();
    }

    public int getMoney()
    {
        return this.money;
    }

    public void setMoney(int money)
    {
        this.money = money;
        this.upload();
    }

    public void close()
    {
        this.members.forEach(m -> m.sendMessage(Component.text(String.format("%s は Guild マスターによって解散されました", this.name)).color(NamedTextColor.RED)));
        this.members.forEach(m -> m.setGuild(null));

        Guild.instances.remove(this);

        try
        {
            Connection con = DriverManager.getConnection(Reincarnation.DB_URL, Reincarnation.DB_USER, Reincarnation.DB_PASS);
            PreparedStatement stmt = con.prepareStatement("DELETE FROM guild WHERE id = ?");
            stmt.setString(1, this.id.toString());
            stmt.executeUpdate();

            stmt.close();
            con.close();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void join(User user)
    {
        this.members.add(user);
        user.setGuild(this);

        this.members.forEach(m -> m.sendMessage(user.getRankedName()
                .append(Component.text(" が Guild に参加しました！").color(NamedTextColor.YELLOW))));
    }

    public void quit(User user)
    {
        this.members.remove(user);
        user.setGuild(null);

        this.members.forEach(m -> m.sendMessage(user.getRankedName()
                .append(Component.text(" が Guild から退出しました").color(NamedTextColor.YELLOW))));

        user.sendMessage(Component.text("Guild から退出しました").color(NamedTextColor.RED));
    }

    public void upload()
    {
        try
        {
            Connection con = DriverManager.getConnection(Reincarnation.DB_URL, Reincarnation.DB_USER, Reincarnation.DB_PASS);
            PreparedStatement stmt = con.prepareStatement("UPDATE guild SET name = ?, master = ?, exp = ?, money = ? WHERE id = ?");
            stmt.setString(1, this.name);
            stmt.setString(2, this.master.getId().toString());
            stmt.setInt(3, this.exp);
            stmt.setInt(4, this.money);
            stmt.setString(5, this.id.toString());

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
