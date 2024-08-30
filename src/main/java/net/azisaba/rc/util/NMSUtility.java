package net.azisaba.rc.util;

import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.WorldServer;
import net.minecraft.server.network.PlayerConnection;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class NMSUtility
{
    public static CraftPlayer getCraftPlayer(Player player)
    {
        return (CraftPlayer) player;
    }

    public static WorldServer getWorldServer(Player player)
    {
        return NMSUtility.getCraftPlayer(player).getHandle().getWorldServer();
    }

    public static PlayerConnection getConnection(Player player)
    {
        return NMSUtility.getCraftPlayer(player).getHandle().b;
    }

    public static void sendPacket(Player player, Packet<? extends PacketListener> packet)
    {
        NMSUtility.getConnection(player).sendPacket(packet);
    }
}
