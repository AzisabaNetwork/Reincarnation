package net.azisaba.rc.user;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;

public enum UserRank
{
    OWNER(Component.text("OWNER").color(NamedTextColor.RED)),
    ADMIN(Component.text("ADMIN").color(NamedTextColor.RED)),
    MVP_PLUS(Component.text("MVP").color(NamedTextColor.AQUA).append(Component.text("+").color(NamedTextColor.RED))),
    MVP(Component.text("MVP").color(NamedTextColor.AQUA)),
    VIP_PLUS(Component.text("VIP").color(NamedTextColor.GREEN).append(Component.text("+").color(NamedTextColor.GOLD))),
    VIP(Component.text("VIP").color(NamedTextColor.GREEN)),
    DEFAULT(Component.text("").color(NamedTextColor.GRAY));

    private final Component display;

    UserRank(Component display)
    {
        this.display = display;
    }

    public Component getDisplay()
    {
        return this.display;
    }

    public Component getRankedName(String name)
    {
        TextComponent textComponent = (TextComponent) this.display;

        if (textComponent.content().isBlank())
        {
            return Component.text(name).color(NamedTextColor.GRAY);
        }
        else
        {
            TextColor theme = textComponent.color();
            TextComponent rank = Component.text("[").color(theme).append(this.display).append(Component.text("] ").color(theme));
            return rank.append(Component.text(name));
        }
    }

    public Component getRankedName(Player player)
    {
        return this.getRankedName(player.getName());
    }
}
