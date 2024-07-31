package net.azisaba.rc.user;

import net.azisaba.rc.Reincarnation;
import net.azisaba.rc.guild.Guild;
import net.azisaba.rc.quest.Quest;
import net.azisaba.rc.quest.QuestEngine;
import net.azisaba.rc.util.UserUtil;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.ArrayList;

public class User
{
    private static final ArrayList<User> instances = new ArrayList<>();

    public static User getInstance(String id)
    {
        ArrayList<User> filteredInstances = new ArrayList<>(User.instances.stream().filter(i -> i.getId().equals(id)).toList());
        return filteredInstances.isEmpty() ? UserUtil.exits(id) ? new User(id) : new User(id, null, Rank.DEFAULT, null, 0, 0) : filteredInstances.get(0);
    }

    public static User getInstance(Player player)
    {
        return User.getInstance(player.getUniqueId().toString());
    }

    private final String id;

    private String name;
    private Rank rank;
    private Guild guild;
    private int exp;
    private int money;

    private final ArrayList<QuestEngine> unlocks = new ArrayList<>();

    private User(String id)
    {
        this.id = id;

        try
        {
            Connection con = DriverManager.getConnection(Reincarnation.DB_URL, Reincarnation.DB_USER, Reincarnation.DB_PASS);
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM user WHERE id = ?");
            stmt.setString(1, this.id);
            ResultSet rs = stmt.executeQuery();

            rs.next();

            this.name = rs.getString("name");
            this.rank = Rank.valueOf(rs.getString("role"));
            this.exp = rs.getInt("exp");
            this.money = rs.getInt("money");

            rs.close();
            stmt.close();

            PreparedStatement stmt2 = con.prepareStatement("SELECT id FROM quest WHERE user = ?");
            stmt2.setString(1, this.id);
            ResultSet rs2  = stmt2.executeQuery();

            while (rs2.next())
            {
                this.unlocks.add(QuestEngine.getInstance(rs2.getString("id")));
            }

            rs2.close();
            stmt2.close();

            con.close();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        User.instances.add(this);
    }

    private User(String id, String name, Rank rank, Guild guild, int exp, int money)
    {
        this.id = id;
        this.name = name;
        this.rank = rank;
        this.guild = guild;
        this.exp = exp;
        this.money = money;

        try
        {
            Connection con = DriverManager.getConnection(Reincarnation.DB_URL, Reincarnation.DB_USER, Reincarnation.DB_PASS);
            PreparedStatement stmt = con.prepareStatement("INSERT INTO user VALUES(?, ?, ?, ?, ?, ?)");
            stmt.setString(1, this.id);
            stmt.setString(2, this.name);
            stmt.setString(3, this.rank.toString());
            stmt.setString(4, (this.guild == null) ? null : this.guild.getId());
            stmt.setInt(5, this.exp);
            stmt.setInt(6, this.money);

            stmt.executeUpdate();
            stmt.close();
            con.close();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        User.instances.add(this);
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

    public Rank getRank()
    {
        return (this.rank == null) ? Rank.DEFAULT : this.rank;
    }

    public void setRank(Rank rank)
    {
        this.rank = rank;
        this.upload();
    }

    public Guild getGuild()
    {
        return this.guild;
    }

    public void setGuild(Guild guild)
    {
        this.guild = guild;
        this.upload();
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

    public void unlock(QuestEngine quest)
    {
        if (this.unlocked(quest))
        {
            return;
        }

        this.unlocks.add(quest);
        this.upload();
    }

    public boolean unlocked(QuestEngine quest)
    {
        return this.unlocks.contains(quest);
    }

    public void upload()
    {
        try
        {
            Connection con = DriverManager.getConnection(Reincarnation.DB_URL, Reincarnation.DB_USER, Reincarnation.DB_PASS);
            PreparedStatement stmt = con.prepareStatement("UPDATE user SET name = ?, role = ?, guild = ?, exp = ?, money = ? WHERE id = ?");
            stmt.setString(1, this.name);
            stmt.setString(2, this.rank.toString());
            stmt.setString(3, (this.guild == null) ? null : this.guild.getId());
            stmt.setInt(4, this.exp);
            stmt.setInt(5, this.money);
            stmt.setString(6, this.id);

            stmt.executeUpdate();

            PreparedStatement stmt2 = con.prepareStatement("DELETE FROM quest WHERE user = ?");
            stmt2.setString(1, this.id);
            stmt2.executeUpdate();
            stmt2.close();

            PreparedStatement stmt3 = con.prepareStatement("INSERT INTO quest VALUES(?, ?)");

            for (QuestEngine quest : this.unlocks)
            {
                stmt3.setString(1, quest.getId());
                stmt3.setString(2, this.id);
                stmt3.executeUpdate();
            }

            stmt3.close();

            stmt.close();
            con.close();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}
