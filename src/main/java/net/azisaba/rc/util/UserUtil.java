package net.azisaba.rc.util;

import net.azisaba.rc.Reincarnation;
import net.azisaba.rc.ui.AnimationBuilder;
import net.azisaba.rc.ui.SidePanel;
import net.azisaba.rc.user.User;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.*;
import java.util.Date;

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

        User.getInstances().forEach(u -> u.friendLoadedFlag = true);
    }

    public static void sidePanel(Player player)
    {
        SidePanel panel = new SidePanel();
        panel.addRows(8);
        panel.addRow(Component.text("www.azisaba.net").color(NamedTextColor.YELLOW));

        panel.setRunnable(new BukkitRunnable()
        {
            private final Component[] title = AnimationBuilder.HYPIXEL("  REINCARNATION  ");
            private final User user = User.getInstance(player);
            private int tick = 0;

            @Override
            public void run()
            {
                panel.setTitle(this.title[this.tick]);
                panel.setRow(0, Component.text(Reincarnation.sdf1.format(new Date())).color(NamedTextColor.GRAY));
                panel.setRow(2, Component.text("Rank: ").append(this.user.getRank().getDisplay()));
                panel.setRow(3, Component.text("所持金: ").append(Component.text(this.user.getMoney() + " RI").color(NamedTextColor.GREEN)));
                panel.setRow(4, Component.text("レベル: ").append(Component.text(this.user.getExp() / 10).color(NamedTextColor.GREEN)));
                panel.setRow(6, Component.text("Guild: ").append(Component.text((this.user.getGuild() == null) ? "なし" : this.user.getGuild().getName()).color(NamedTextColor.DARK_GREEN)));

                this.tick = (this.title.length <= this.tick + 1) ? 0 : this.tick + 1;
            }
        }, 10L);

        panel.addPlayer(player);
    }
}
