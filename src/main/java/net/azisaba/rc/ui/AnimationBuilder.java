package net.azisaba.rc.ui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class AnimationBuilder
{
    public static Component[] GAMING(String src)
    {
        NamedTextColor[] colors = {NamedTextColor.RED, NamedTextColor.YELLOW, NamedTextColor.GREEN, NamedTextColor.AQUA, NamedTextColor.BLUE, NamedTextColor.DARK_PURPLE, NamedTextColor.LIGHT_PURPLE};
        Component[] animated = new Component[src.length()];

        for (int i = 0; i < animated.length; i ++)
        {
            NamedTextColor color = (colors.length <= i) ? colors[i % colors.length] : colors[i];
            animated[i] = Component.text(src, color, TextDecoration.BOLD);
        }

        return animated;
    }

    public static Component[] HYPIXEL(String src)
    {
        Component[] animated = new Component[src.length() + 4];

        for (int i = 0; i < src.length(); i ++)
        {
            Component component = Component.text("");

            if (i != 0)
            {
                component = component.append(Component.text(src.substring(0, i), NamedTextColor.WHITE, TextDecoration.BOLD));
            }

            component = component.append(Component.text(src.charAt(i), NamedTextColor.GOLD, TextDecoration.BOLD));

            if (src.length() - 1 != i)
            {
                component = component.append(Component.text(src.substring(i + 1), NamedTextColor.YELLOW, TextDecoration.BOLD));
            }

            animated[i] = component;
        }

        for (int i = src.length(); i < animated.length; i ++)
        {
            Component component = Component.text(src).color((i % 2 == 0) ? NamedTextColor.WHITE : NamedTextColor.YELLOW).decorate(TextDecoration.BOLD);
            animated[i] = component;
        }

        return animated;
    }
}
