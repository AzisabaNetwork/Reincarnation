package net.azisaba.rc.scenario.hologram;

import net.azisaba.rc.util.NMSUtil;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy;
import net.minecraft.network.protocol.game.PacketPlayOutEntityMetadata;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntity;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.decoration.EntityArmorStand;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Hologram
{
    private final String[] rows;
    private final ArrayList<EntityArmorStand> stands = new ArrayList<>();

    public Hologram(String... rows)
    {
        this.rows = rows;
    }

    public void spawn(Location location, Player viewer)
    {
        WorldServer server = ((CraftPlayer) viewer).getHandle().getWorldServer();;

        for (String row : this.rows)
        {
            EntityArmorStand stand = new EntityArmorStand(EntityTypes.c, server);
            stand.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
            stand.setInvisible(true);
            stand.setNoGravity(false);
            stand.setInvulnerable(true);

            stand.setCustomName(IChatBaseComponent.a(row));
            stand.setCustomNameVisible(true);

            PacketPlayOutSpawnEntity packet = new PacketPlayOutSpawnEntity(stand);
            NMSUtil.sendPacket(viewer, packet);

            PacketPlayOutEntityMetadata packet2 = new PacketPlayOutEntityMetadata(stand.getId(), stand.getDataWatcher(), true);
            NMSUtil.sendPacket(viewer, packet2);

            location.subtract(0, 0.25, 0);
            this.stands.add(stand);
        }
    }

    public void despawn(Player player)
    {
        for (EntityArmorStand stand : this.stands)
        {
            PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(stand.getId());
            NMSUtil.sendPacket(player, packet);
        }
    }
}
